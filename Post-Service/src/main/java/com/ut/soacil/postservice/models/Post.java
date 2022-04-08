package com.ut.soacil.postservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "posts")
@Getter
@Setter
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
