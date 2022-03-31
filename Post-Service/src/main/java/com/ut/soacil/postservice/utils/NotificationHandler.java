package com.ut.soacil.postservice.utils;

import com.ut.soacil.postservice.models.NotificationBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class NotificationHandler {
	private static final String NOTIFICATION_SERVICE_ID = "notifications";
	private static final String NOTIFICATION_SEND_ENDPOINT = "http://localhost:9091/v1/" + NOTIFICATION_SERVICE_ID + "/";

	public static void sendNotification(NotificationBody notificationBody) {
		HttpEntity<NotificationBody> httpEntity = new HttpEntity<>(notificationBody);
		new RestTemplate().exchange(NOTIFICATION_SEND_ENDPOINT, HttpMethod.POST,
				httpEntity, NotificationBody.class);
	}
}
