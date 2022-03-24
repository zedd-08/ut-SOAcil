package com.ut.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JsonProperty(value = "notification-id")
    private Integer id;

    @Column(name= "description", nullable = false)
    @JsonProperty(value = "description", required = true)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    @JsonProperty("created_at")
    private Instant createdAt = Instant.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification {" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
