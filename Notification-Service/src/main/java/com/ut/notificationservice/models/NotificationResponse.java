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
    private String user_id;

    @JsonProperty("description")
    @NotBlank
    private String description;

    @JsonProperty("created_at")
    @NotNull
    @PastOrPresent
    private Instant createdAt;

    public NotificationResponse(Integer id, @NotBlank String user_id, @NotBlank String description, @NotNull @PastOrPresent Instant createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.description = description;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification {" +
                "id=" + id +
                ", author_id='" + user_id + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
