package com.ut.notificationservice.controller;

import com.ut.notificationservice.models.Auth0Body;
import com.ut.notificationservice.models.Notification;
import com.ut.notificationservice.services.NotificationService;
import com.ut.notificationservice.util.AuthenticateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = { "/v1/notification-service" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/{userId}")
	@Operation(summary = "Get notification by User-ID.", parameters = {
			@Parameter(in = ParameterIn.HEADER, name = "auth_token", description = "Authentication token provided for the user", required = true, schema = @Schema(implementation = String.class)),
			@Parameter(in = ParameterIn.PATH, name = "userId", description = "User ID", required = true, schema = @Schema(implementation = Integer.class))
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Notification.class)))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public ResponseEntity<List<Notification>> findByUserId(@PathVariable(value = "userId") Integer userId,
			@RequestHeader("auth_token") String authToken) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(notificationService.findNotificationsByUserID(userId));
	}
}