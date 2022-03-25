package com.ut.soacil.postservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class Notification implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty( "description")
    private String description;

    @JsonProperty("created_at")
    private Instant created_at = Instant.now();

    public Notification() { }

    public Notification(Integer user_id, String description) {
        this.user_id = user_id;
        this.description = description;
    }

    public Integer getId() {
        return user_id;
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
