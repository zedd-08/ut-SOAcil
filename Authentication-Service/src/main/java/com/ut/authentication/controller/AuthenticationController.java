package com.ut.authentication.controller;

import com.ut.authentication.models.Auth0;
import com.ut.authentication.models.User;
import com.ut.authentication.repository.Auth0Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@CrossOrigin("*")
@RestController
@RequestMapping(path = { "/v1/auth-service" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
	@Autowired
	private Auth0Repository authRepository;

	private static final Long TIMEOUT = 30L;

	@PostMapping(path = "/getauthtoken")
	@Operation(summary = "Generate an authentication token for a user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Auth0.class))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Auth0> getNewAuthToken(@Validated @RequestBody User userResponse) {
		Auth0 auth = new Auth0();
		if (!isPasswordValid(userResponse)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		auth.setAuth_token();
		auth.setUser_id(userResponse.getUser_id());
		authRepository.save(auth);
		return ResponseEntity.ok(auth);
	}

	@PostMapping(path = "/isauthvalid")
	@Operation(summary = "Check the validity of authentication token for a user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Boolean.class))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody Boolean isAuthValid(@Validated @RequestBody Auth0 auth0) {
		Auth0 auth = authRepository.findById(auth0.getUser_id()).orElse(null);
		if (auth == null)
			return false;
		if (!auth.getAuth_token().equals(auth0.getAuth_token()))
			return false;
		Instant now = Instant.now();
		Duration dur = Duration.between(auth.getCreated_at(), now);
		return dur.toMinutes() <= TIMEOUT;
	}

	@PostMapping(path = "/login")
	@Operation(summary = "Endpoint for logging in the user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Auth0.class))
			}),
			@ApiResponse(responseCode = "401", description = "Invalid password")
	})
	public @ResponseBody ResponseEntity<Auth0> login(@Validated @RequestBody User userResponse) {
		Auth0 auth = new Auth0();
		auth.setUser_id(userResponse.getUser_id());
		if (isPasswordValid(userResponse)) {
			auth.setAuth_token();
			authRepository.save(auth);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(auth);
	}

	@PostMapping(path = "/logout")
	@Operation(summary = "Endpoint for logging out the user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Boolean.class))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody Boolean logout(@Validated @RequestBody User userResponse) {
		User actualUser = authRepository.findUserById(userResponse.getUser_id());
		authRepository.findById(actualUser.getUser_id())
				.ifPresent(auth0 -> authRepository.deleteById(auth0.getUser_id()));
		return true;
	}

	private boolean isPasswordValid(User userResponse) {
		User actual = authRepository.findUserById(userResponse.getUser_id());
		return actual != null && actual.getPassword().equals(userResponse.getPassword());
	}
}