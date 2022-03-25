package com.ut.notificationservice.repository;

import com.ut.notificationservice.models.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    @Query("SELECT n " +
            "FROM Notification n " +
            "WHERE n.user_id = :user_id ORDER BY n.created_at DESC")
    List<Notification> findNotificationsByUser(@Param("user_id") Integer user_id);
}
