package com.ut.notificationservice.services;

import com.ut.notificationservice.models.Notification;
import com.ut.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NotificationService {
    @Autowired private NotificationRepository notificationRepository;
    @Autowired RestTemplate restTemplate;

    @Transactional(readOnly = true)
    public List<Notification> findNotificationsByUserID(String user_id) {
        return notificationRepository.findNotificationsByUser(user_id);
    }

    @Transactional
    public Notification save(Notification notification) {
        notificationRepository.save(notification);
        return notification;
    }

}
