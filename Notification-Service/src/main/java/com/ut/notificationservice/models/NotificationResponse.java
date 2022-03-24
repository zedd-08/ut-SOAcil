package com.ut.notificationservice.models;

import java.util.List;

public class NotificationResponse {
    private List<Notification> notifications;

    public NotificationResponse(List<Notification> notifications) {  this.notifications = notifications; }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
