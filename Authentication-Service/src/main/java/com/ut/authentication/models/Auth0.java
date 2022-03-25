package com.ut.authentication.models;

import java.security.SecureRandom;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "authtoken")
public class Auth0 {
	@Id
	@Column(name = "id", nullable = false)
	@JsonProperty(value = "userId")
	private Integer id;

	@Column(name = "auth_token", nullable = false)
	@JsonProperty(value = "auth_token")
	private String auth_token;

	@CreationTimestamp
	@Column(name = "created", updatable = false, nullable = false)
	@JsonProperty("created")
	private Instant created = Instant.now();

	private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Instant getCreated() {
		return created;
	}
}
