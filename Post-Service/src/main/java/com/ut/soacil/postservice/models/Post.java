package com.ut.soacil.postservice.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	@JsonProperty(value = "post-id")
	private Integer id;

	@Column(name = "content", nullable = false)
	@JsonProperty(value = "content")
	private String content;

	@Column(name = "userId", nullable = false)
	@JsonProperty(value = "userId")
	private String userId;

	@Column(name = "likes", nullable = false)
	@JsonProperty(value = "likes")
	private Integer likes;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	@JsonProperty("created_at")
	private Instant createdAt = Instant.now();

	public Integer getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Post {" +
				"content=\'" + content +
				"\'', createdAt=" + createdAt +
				", id=" + id +
				", likes=" + likes +
				", userId=" + userId + "}";
	}

}
