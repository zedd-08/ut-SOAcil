package com.ut.notificationservice.repository;

import com.ut.notificationservice.models.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAll();

}
