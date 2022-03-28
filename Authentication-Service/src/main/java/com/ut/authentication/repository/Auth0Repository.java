package com.ut.authentication.repository;

import com.ut.authentication.models.Auth0;

import com.ut.authentication.models.UserResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("authRepository")
public interface Auth0Repository extends CrudRepository<Auth0, Integer> {
    @Query("SELECT u " +
            "FROM UserResponse u " +
            "WHERE u.user_id = :user_id")
    UserResponse findUserById(@Param("user_id") Integer user_id);
}
