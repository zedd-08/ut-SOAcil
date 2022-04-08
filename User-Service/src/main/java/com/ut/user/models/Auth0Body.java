package com.ut.user.models;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Auth0Body implements Serializable {
	@JsonProperty(value = "user_id")
	private Integer user_id;

	@JsonProperty(value = "auth_token")
	private String auth_token;

	@JsonProperty(value = "created_at")
	private Instant created_at;
}
