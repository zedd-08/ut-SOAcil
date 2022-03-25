package com.ut.notificationservice.models;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationResponse toNotificationResponse(Notification notification);

    List<NotificationResponse> toNotificationResponses(List<Notification> notifications);
}
