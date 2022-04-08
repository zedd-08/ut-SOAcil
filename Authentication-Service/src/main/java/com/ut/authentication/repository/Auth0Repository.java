package com.ut.authentication.repository;

import com.ut.authentication.models.Auth0;

import com.ut.authentication.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("authRepository")
public interface Auth0Repository extends CrudRepository<Auth0, Integer> {
    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.user_id = :user_id")
    User findUserById(@Param("user_id") Integer user_id);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.user_handle = :user_handle")
    User findUserByhandle(@Param("user_handle") String user_handle);
}
