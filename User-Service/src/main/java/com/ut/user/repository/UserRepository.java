package com.ut.user.repository;

import com.ut.user.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("mainUserRepository")
public interface UserRepository extends CrudRepository<User, Integer> { }
