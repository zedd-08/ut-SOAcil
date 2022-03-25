package com.ut.notificationservice.messagequeue;

import com.ut.notificationservice.models.Notification;
import com.ut.notificationservice.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationReceiver implements RabbitListenerConfigurer {
    @Autowired NotificationService notificationService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) { }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(Notification notification) {
        try {
            notificationService.save(notification);
        } catch (Exception ignored) {}
    }
}
