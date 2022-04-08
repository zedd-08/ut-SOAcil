package com.ut.user.util;

import com.ut.user.models.NotificationBody;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class NotificationHandler {
	private static final String NOTIFICATION_SERVICE_ENDPOINT = "http://localhost:9091/v1/notifications";

	public static void sendNotification(NotificationBody notificationBody) {
		HttpEntity<NotificationBody> httpEntity = new HttpEntity<>(notificationBody);
		new RestTemplate().exchange(NOTIFICATION_SERVICE_ENDPOINT + "/" + notificationBody.getUser_id(),
				HttpMethod.POST,
				httpEntity, NotificationBody.class);
	}
}
