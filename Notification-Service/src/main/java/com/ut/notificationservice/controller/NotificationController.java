package com.ut.notificationservice.controller;

import com.ut.notificationservice.models.NotificationResponse;
import com.ut.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/notification")
    public NotificationResponse getAllNotifications(){
        return new NotificationResponse(notificationRepository.findAll());
    }
}
