package com.ut.user.controller;

import com.ut.user.models.User;
import com.ut.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

@CrossOrigin("*")
@RestController
@RequestMapping(
		path = {"/v1/users"},
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/{userId}/friends")
	@Operation(summary = "Get friends of a given User.")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							content = {
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											array = @ArraySchema(arraySchema = @Schema(implementation = Integer.class)))
							}),
					@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
			})
	public @ResponseBody ResponseEntity<Iterable<Integer>> getUserFriends(@PathVariable("userId") Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		return user == null ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(user.getFriends());
	}

	@PostMapping(path = "/create")
	@Operation(summary = "Create a new user.")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							content = {
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											schema = @Schema(implementation = User.class))
							}),
					@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
			})
	public @ResponseBody ResponseEntity<User> createNewUser(@RequestBody User user) {
		User userToAdd = new User();
		userToAdd.setFirstName(user.getFirstName());
		userToAdd.setLastName(user.getLastName());
		userToAdd.setPassword(user.getPassword());
		userRepository.save(userToAdd);
		return ResponseEntity.ok(userToAdd);
	}
}
