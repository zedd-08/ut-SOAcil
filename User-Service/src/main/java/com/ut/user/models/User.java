package com.ut.user.models;

import java.time.Instant;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	@JsonProperty(value = "userId")
	private Integer id;

	@Column(name = "firstName", nullable = false)
	@JsonProperty(value = "firstName")
	private String firstName;

	@Column(name = "lastName", nullable = false)
	@JsonProperty(value = "lastName")
	private String lastName;

	@Column(name = "password", nullable = false)
	@JsonProperty(value = "password")
	private String password;

	@Column(name = "bio", nullable = false)
	@JsonProperty(value = "bio")
	private String bio;

	@Column(name = "friends", nullable = false)
	@JsonProperty(value = "friends")
	private ArrayList<User> friends;

	@CreationTimestamp
	@Column(name = "joined", updatable = false, nullable = false)
	@JsonProperty("joined")
	private Instant joined = Instant.now();

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public ArrayList<User> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	public Instant getJoined() {
		return joined;
	}
}