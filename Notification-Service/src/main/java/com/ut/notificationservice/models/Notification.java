package com.ut.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name= "user_id", unique = true)
    @NotEmpty
    @JsonProperty(value = "user_id", required = true)
    private String user_id;

    @Column(name= "description")
    @NotEmpty
    @JsonProperty(value = "description", required = true)
    private String description;

    @NotNull
    @CreationTimestamp
    @JsonProperty(value = "created_at")
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant created_at = Instant.now();

    public Notification() { }

    public Notification(String user_id, String description) {
        this.user_id = user_id;
        this.description = description;
    }

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

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notification givenObject = (Notification) obj;
        if (!Objects.equals(this.user_id, givenObject.user_id)) {
            return false;
        }
        if (!Objects.equals(this.description, givenObject.description)) {
            return false;
        }
        return Objects.equals(this.id, givenObject.id);
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
