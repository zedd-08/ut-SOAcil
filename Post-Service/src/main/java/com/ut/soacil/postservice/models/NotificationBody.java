package com.ut.soacil.postservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationBody implements Serializable {
    @JsonProperty(value = "user_id")
    private Integer user_id;

    @JsonProperty(value = "description")
    private String description;

    public NotificationBody() { }

    public NotificationBody(Integer user_id, String description) {
        this.user_id = user_id;
        this.description = description;
    }
}
