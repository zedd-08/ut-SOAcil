package com.ut.notificationservice.controller;

import com.ut.notificationservice.models.Notification;
import com.ut.notificationservice.models.NotificationMapper;
import com.ut.notificationservice.models.NotificationResponse;
import com.ut.notificationservice.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(
        path = {"/v1/notifications"},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {
    @Autowired private NotificationService notificationService;
    @Autowired private NotificationMapper notificationMapper;
    @Autowired private RabbitTemplate rabbitTemplate;

    @GetMapping("/{user-id}")
    @Operation(summary = "Get notification by User-ID.")
    @ApiResponses (
        value = {
                @ApiResponse (
                        responseCode = "200",
                        content = {
                                @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Notification.class))
                        })
        })
    public ResponseEntity<List<NotificationResponse>> findByUserId(@PathVariable(value = "user-id") Integer userId) {
        return ResponseEntity.ok(notificationMapper.toNotificationResponses(notificationService.findNotificationsByUserID(userId)));
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Post a notification to send to users.")
    @ApiResponse (
            responseCode = "201",
            content = {
               @Content(
                       mediaType = MediaType.APPLICATION_JSON_VALUE,
                       schema = @Schema(implementation = Notification.class)
               )
            })
    public ResponseEntity<NotificationResponse> createNotification(@Validated @RequestBody Notification notification) {
        rabbitTemplate.convertAndSend(notification);
        final Notification newNotification = notificationService.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationMapper.toNotificationResponse(newNotification));
    }
}
