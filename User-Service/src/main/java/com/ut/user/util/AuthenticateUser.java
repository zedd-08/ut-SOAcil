package com.ut.user.util;

import com.ut.user.models.Auth0Body;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AuthenticateUser {
	private static final String AUTH_SERVICE_CHECK_ENDPOINT = "http://localhost:9090/v1/auth-service";
	private static final String AUTH_SERVICE_IS_VALID = "/isauthvalid";
	private static final String AUTH_SERVICE_LOGIN = "/login";

	public static boolean isAuthValid(Auth0Body auth0Body) {
		HttpEntity<Auth0Body> httpEntity = new HttpEntity<>(auth0Body);
		ResponseEntity<Boolean> isAuthValid = new RestTemplate().exchange(
				AUTH_SERVICE_CHECK_ENDPOINT + AUTH_SERVICE_IS_VALID, HttpMethod.POST,
				httpEntity, Boolean.class);
		return isAuthValid.getBody();
	}

	public static ResponseEntity<Auth0Body> loginUser(String userHandle, String password) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("user_handle", userHandle);
		headers.add("password", password);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<Auth0Body> auth0 = new RestTemplate().exchange(AUTH_SERVICE_CHECK_ENDPOINT + AUTH_SERVICE_LOGIN,
				HttpMethod.POST,
				httpEntity, Auth0Body.class);
		return auth0;
	}
}
