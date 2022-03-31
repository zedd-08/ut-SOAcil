package com.ut.notificationservice.messagequeue;

import com.ut.notificationservice.models.Notification;
import com.ut.notificationservice.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationReceiver {
    @Autowired NotificationService notificationService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(Notification notification) {
        try {
            notificationService.save(notification);
        } catch (Exception ignored) {}
    }
}
