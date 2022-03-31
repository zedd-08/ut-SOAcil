package com.ut.authentication.models;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "authtoken")
public class Auth0 implements Serializable {
	@Id
	@NotNull
	@Column(name = "user_id", nullable = false)
	@JsonProperty(value = "user_id", required = true)
	private Integer user_id;

	@NotEmpty
	@Column(name = "auth_token")
	@JsonProperty(value = "auth_token", required = true)
	private String auth_token;

	@NotNull
	@CreationTimestamp
	@Column(name = "created_at")
	@JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
	private Instant created_at = Instant.now();

	private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token() {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<25; i++) {
			int index = random.nextInt(ALL_CHARS.length());
			sb.append(ALL_CHARS.charAt(index));
		}
		auth_token = sb.toString();
	}

	public Instant getCreated_at() {
		return created_at;
	}
}
