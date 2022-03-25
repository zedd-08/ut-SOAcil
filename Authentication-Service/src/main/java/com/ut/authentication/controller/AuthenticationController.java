package com.ut.authentication.controller;

import java.time.Duration;
import java.time.Instant;

import com.ut.authentication.models.Auth0;
import com.ut.authentication.models.User;
import com.ut.authentication.repository.Auth0Repository;
import com.ut.authentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {
	@Autowired
	private Auth0Repository authRepository;
	@Autowired
	private UserRepository userRepository;

	@PostMapping(path = "/getauthtoken")
	public @ResponseBody Auth0 getNewAuthToken(@RequestBody User user) {
		Auth0 auth = new Auth0();
		auth.setAuth_token();
		auth.setId(user.getId());
		authRepository.save(auth);
		return auth;
	}

	@PostMapping(path = "/isauthvalid")
	public @ResponseBody Boolean isAuthValid(@RequestBody Auth0 auth0) {
		Auth0 auth = authRepository.findById(auth0.getId()).orElse(null);
		if (auth == null)
			return false;
		if (!auth.getAuth_token().equals(auth0.getAuth_token()))
			return false;
		Instant now = Instant.now();
		Duration dur = Duration.between(auth.getCreated(), now);
		return dur.toMinutes() > 5 ? false : true;
	}

	@PostMapping(path = "/login")
	public @ResponseBody Auth0 login(@RequestBody User user) {
		Auth0 auth = new Auth0();
		if (isPasswordValid(user)) {
			auth.setAuth_token();
		}
		auth.setId(user.getId());
		return auth;
	}

	private boolean isPasswordValid(User user) {
		User actual = userRepository.findById(user.getId()).orElse(null);
		return actual.getPassword().equals(user.getPassword()) ? true : false;
	}

	@PostMapping(path = "/logout")
	public @ResponseBody Boolean logout(@RequestBody Auth0 auth) {
		Auth0 auth0 = authRepository.findById(auth.getId()).orElse(null);
		if (null != auth0) {
			authRepository.deleteById(auth.getId());
		}
		return true;
	}
}