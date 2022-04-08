package com.ut.soacil.postservice.utils;

import com.ut.soacil.postservice.models.NotificationBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class NotificationHandler {
	private static final String NOTIFICATION_SERVICE_ENDPOINT = "http://localhost:9091/v1/notification-service";

	public static void sendNotification(NotificationBody notificationBody, MultiValueMap<String, String> headers) {
		HttpEntity<NotificationBody> httpEntity = new HttpEntity<>(notificationBody, headers);

		new RestTemplate().exchange(NOTIFICATION_SERVICE_ENDPOINT + "/" + notificationBody.getUser_id(),
				HttpMethod.POST,
				httpEntity, String.class);
	}
}
