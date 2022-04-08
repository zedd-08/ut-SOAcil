package com.ut.notificationservice.util;

import com.ut.notificationservice.models.Auth0Body;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthenticateUser {
	private static final String AUTH_SERVICE_CHECK_ENDPOINT = "http://localhost:9090/v1/auth-service";
	private static final String AUTH_SERVICE_IS_VALID = "/isauthvalid";

	public static boolean isAuthValid(Auth0Body auth0Body) {
		HttpEntity<Auth0Body> httpEntity = new HttpEntity<>(auth0Body);
		ResponseEntity<Boolean> isAuthValid = new RestTemplate().exchange(
				AUTH_SERVICE_CHECK_ENDPOINT + AUTH_SERVICE_IS_VALID, HttpMethod.POST,
				httpEntity, Boolean.class);
		return Boolean.TRUE.equals(isAuthValid.getBody());
	}
}
