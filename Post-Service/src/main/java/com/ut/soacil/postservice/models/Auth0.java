package com.ut.soacil.postservice.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth0 {
	@JsonProperty(value = "userId")
	private Integer id;

	@JsonProperty(value = "auth_token")
	private String auth_token;

	@JsonProperty("created")
	private Instant created = Instant.now();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public Instant getCreated() {
		return created;
	}
}
