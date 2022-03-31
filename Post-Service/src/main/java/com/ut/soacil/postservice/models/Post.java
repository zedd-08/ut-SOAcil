package com.ut.soacil.postservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "posts")
public class Post implements Serializable {
	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "post_id")
	@JsonProperty(value = "post_id", access = JsonProperty.Access.READ_ONLY)
	private Integer post_id;

	@NotEmpty
	@Column(name = "content")
	@JsonProperty(value = "content")
	private String content;

	@NotNull
	@Column(name = "user_id")
	@JsonProperty(value = "user_id")
	private Integer user_id;

	@NotNull
	@Column(name = "likes")
	@JsonProperty(value = "likes", access = JsonProperty.Access.READ_ONLY)
	private Integer likes = 0;

	@NotNull
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	@JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
	private Instant created_at = Instant.now();

	public Integer getPost_id() {
		return post_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Instant getCreated_at() {
		return created_at;
	}

	@Override
	public String toString() {
		return "Post {" +
				"content='" + content +
				"', createdAt=" + created_at +
				", post_id=" + post_id +
				", likes=" + likes +
				", user_id=" + user_id + "}";
	}

}
