package com.ut.authentication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "users")
@Getter
public class User implements Serializable {
	@NotNull
	@Id
	@Column(name = "user_id", nullable = false)
	@JsonProperty(value = "user_id")
	private Integer user_id;

	@Column(name = "user_handle", nullable = false)
	@JsonProperty(value = "user_handle")
	private String user_handle;

	@NotEmpty
	@Column(name = "password")
	@JsonProperty(value = "password")
	private String password;
}
