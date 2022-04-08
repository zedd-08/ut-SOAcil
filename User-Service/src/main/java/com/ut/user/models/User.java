package com.ut.user.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false)
	@JsonProperty(value = "user_id", access = JsonProperty.Access.READ_ONLY)
	private Integer user_id;

	@Column(name = "user_handle", nullable = false, unique = true)
	@JsonProperty(value = "user_handle")
	private String userHandle;

	@Column(name = "firstName", nullable = false)
	@JsonProperty(value = "firstName", required = true)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	@JsonProperty(value = "lastName", required = true)
	private String lastName;

	@Column(name = "password", nullable = false)
	@JsonProperty(value = "password", required = true)
	private String password;

	@Column(name = "bio", nullable = false)
	@JsonProperty(value = "bio")
	private String bio = "";

	@ElementCollection
	@Column(name = "friends", nullable = false)
	@JsonProperty(value = "friends", access = JsonProperty.Access.READ_ONLY)
	private List<Integer> friends = new ArrayList<>();

	@CreationTimestamp
	@Column(name = "joined", updatable = false, nullable = false)
	@JsonProperty(value = "joined", access = JsonProperty.Access.READ_ONLY)
	private Instant joined = Instant.now();
}
