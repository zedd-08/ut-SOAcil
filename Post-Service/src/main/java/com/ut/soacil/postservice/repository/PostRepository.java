package com.ut.soacil.postservice.repository;

import com.ut.soacil.postservice.models.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
