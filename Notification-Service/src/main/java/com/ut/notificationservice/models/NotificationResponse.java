package com.ut.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

@Data
public class NotificationResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("user_id")
    @NotNull
    private Integer user_id;

    @JsonProperty("description")
    @NotBlank
    private String description;

    @JsonProperty("created_at")
    @NotNull
    @PastOrPresent
    private Instant created_at;

    public NotificationResponse(Integer id, @NotNull Integer user_id, @NotBlank String description, @NotNull @PastOrPresent Instant created_at) {
        this.id = id;
        this.user_id = user_id;
        this.description = description;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Notification {" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
