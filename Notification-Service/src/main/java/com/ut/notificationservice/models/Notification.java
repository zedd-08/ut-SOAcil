package com.ut.notificationservice.models;

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
import java.util.Objects;

@Data
@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Column(name = "user_id")
    @NotNull
    @JsonProperty(value = "user_id", required = true)
    private Integer user_id;

    @Column(name = "from_user_id")
    @NotNull
    @JsonProperty(value = "from_user_id", required = true)
    private Integer from_user_id;

    @Column(name = "description")
    @NotEmpty
    @JsonProperty(value = "description", required = true)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @NotNull
    @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
    private Instant created_at = Instant.now();

    public Notification() {
    }

    public Notification(Integer user_id, String description) {
        this.user_id = user_id;
        this.description = description;
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
        if (!Objects.equals(this.from_user_id, givenObject.from_user_id)) {
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
                ", from_user_id='" + from_user_id + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
