package com.ut.user.controller;

import com.ut.user.models.Auth0Body;
import com.ut.user.models.NotificationBody;
import com.ut.user.models.User;
import com.ut.user.repository.UserRepository;
import com.ut.user.util.AuthenticateUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = { "/v1/user-service" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.routing-key}")
	private String routingKey;

	@GetMapping(path = "/{userId}/friends")
	@Operation(summary = "Get friends of a given User.", parameters = {
			@Parameter(in = ParameterIn.HEADER, name = "auth_token", description = "Authentication token provided for the user", required = true, schema = @Schema(implementation = String.class)),
			@Parameter(in = ParameterIn.HEADER, name = "user_id", description = "User ID of the logged in user", required = true, schema = @Schema(implementation = Integer.class)),
			@Parameter(in = ParameterIn.PATH, name = "userId", description = "User ID to get friend list of", required = true, schema = @Schema(implementation = Integer.class))
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(arraySchema = @Schema(implementation = User.class)))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Iterable<User>> getUserFriends(@PathVariable("userId") Integer otherUserId,
			@RequestHeader("auth_token") String authToken, @RequestHeader("user_id") Integer userId) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User user = userRepository.findById(otherUserId).orElse(null);
		List<Integer> friendList = user == null ? Collections.emptyList() : user.getFriends();
		ArrayList<User> friendListUsers = new ArrayList<>();
		for (int i : friendList) {
			User fUser = userRepository.findById(i).orElse(null);
			friendListUsers.add(fUser);
		}

		return ResponseEntity.ok(friendListUsers);
	}

	@GetMapping(path = "/{userId}")
	@Operation(summary = "Get details of a given User.", parameters = {
			@Parameter(in = ParameterIn.PATH, name = "userId", description = "User ID", required = true, schema = @Schema(implementation = Integer.class))
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<User> getUserDetails(@PathVariable("userId") Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		return ResponseEntity.ok(user);
	}

	@GetMapping(path = "/all")
	@Operation(summary = "Get all users on the platform.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(arraySchema = @Schema(implementation = User.class)))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@PutMapping(path = "/{userId}/add")
	@Operation(summary = "Add another user as a friend. Send a notification of being added as a friend to the other user", parameters = {
			@Parameter(in = ParameterIn.HEADER, name = "auth_token", description = "Authentication token provided for the user", required = true, schema = @Schema(implementation = String.class)),
			@Parameter(in = ParameterIn.HEADER, name = "user_id", description = "User ID of the logged in user", required = true, schema = @Schema(implementation = Integer.class)),
			@Parameter(in = ParameterIn.PATH, name = "userId", description = "User ID of the friend to add", required = true, schema = @Schema(implementation = Integer.class))
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(arraySchema = @Schema(implementation = Boolean.class)))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Boolean> addUserAsFriend(@PathVariable("userId") Integer friendId,
			@RequestHeader("auth_token") String authToken, @RequestHeader("user_id") Integer userId) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		if (user.getFriends().contains(friendId)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
		}

		User friendUser = userRepository.findById(friendId).orElse(null);
		if (null == friendUser) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		List<Integer> friends = user.getFriends();
		friends.add(friendId);
		user.setFriends(friends);
		userRepository.save(user);

		friends = friendUser.getFriends();
		friends.add(userId);
		friendUser.setFriends(friends);
		userRepository.save(friendUser);

		NotificationBody notificationBody = new NotificationBody(friendId, userId,
				user.getFirstName() + " added you as a friend!");
		rabbitTemplate.convertAndSend(exchange, routingKey, notificationBody);
		return ResponseEntity.ok(true);
	}

	@PostMapping(path = "/create")
	@Operation(summary = "Create a new user and log them in automatically.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Auth0Body.class))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Auth0Body> createNewUser(@RequestBody User user) {
		User userToAdd = new User();
		userToAdd.setFirstName(user.getFirstName());
		userToAdd.setLastName(user.getLastName());
		userToAdd.setPassword(user.getPassword());
		userToAdd.setBio(user.getBio());
		userToAdd.setUserHandle(user.getUserHandle());
		userRepository.save(userToAdd);

		return AuthenticateUser.loginUser(user.getUserHandle(), user.getPassword());
	}
}
