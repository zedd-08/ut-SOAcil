package com.ut.soacil.postservice.utils;

import com.ut.soacil.postservice.models.Auth0;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthenticateUser {
	private static final String AUTH_SERVICE_ID = "auth-service";
	private static final String AUTH_SERVICE_CHECK_ENDPOINT = "http://localhost:9091/api/v1/" + AUTH_SERVICE_ID
			+ "/isauthvalid";

	public static boolean isAuthValid(Auth0 auth0) {
		HttpEntity<Auth0> httpEntity = new HttpEntity<Auth0>(auth0);
		ResponseEntity<Boolean> isAuthValid = new RestTemplate().exchange(AUTH_SERVICE_CHECK_ENDPOINT, HttpMethod.POST,
				httpEntity, Boolean.class);
		return isAuthValid.getBody();
	}
}
