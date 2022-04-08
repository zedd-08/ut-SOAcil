package com.ut.user.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationBody implements Serializable {
    @JsonProperty(value = "user_id")
    private Integer user_id;

    @JsonProperty(value = "from_user_id")
    private Integer from_user_id;

    @JsonProperty(value = "description")
    private String description;
}
