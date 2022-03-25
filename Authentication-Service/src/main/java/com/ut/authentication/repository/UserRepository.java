package com.ut.authentication.repository;

import com.ut.authentication.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
