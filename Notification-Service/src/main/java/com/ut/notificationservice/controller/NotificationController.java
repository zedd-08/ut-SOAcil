package com.ut.notificationservice.controller;

import com.ut.notificationservice.models.Notification;
import com.ut.notificationservice.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(
        path = {"/v1/notifications"},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {
    @Autowired private NotificationService notificationService;
    @Autowired private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    @GetMapping("/{user-id}")
    @Operation(summary = "Get notification by User-ID.")
    @ApiResponses (
        value = {
                @ApiResponse (
                        responseCode = "200",
                        content = {
                                @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Notification.class)))
                        }),
                @ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
        })
    public ResponseEntity<List<Notification>> findByUserId(@PathVariable(value = "user-id") Integer userId) {
        return ResponseEntity.ok(notificationService.findNotificationsByUserID(userId));
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @Operation(summary = "Post a notification to send to users.")
    @ApiResponses (
            value = {
                    @ApiResponse (
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = String.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
            }
    )
    public ResponseEntity<String> createNotification(@Validated @RequestBody Notification notification) {
        if (notification.getCreated_at() == null) notification.setCreated_at(Instant.now());
        rabbitTemplate.convertAndSend(exchange, routingKey, notification);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created the notification.");
    }
}