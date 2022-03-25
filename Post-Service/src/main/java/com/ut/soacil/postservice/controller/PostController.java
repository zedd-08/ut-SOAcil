package com.ut.soacil.postservice.controller;

import com.ut.soacil.postservice.models.Post;
import com.ut.soacil.postservice.models.Auth0;
import com.ut.soacil.postservice.repository.PostRepository;
import com.ut.soacil.postservice.utils.AuthenticateUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
	@Autowired
	private PostRepository postRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@GetMapping(path = "/{postId}")
	public @ResponseBody ResponseEntity<Post> getPostById(@PathVariable("postId") Integer postId) {
		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<Post> addNewPost(@RequestBody Post postReq,
			@RequestHeader("auth_token") String authToken, @RequestHeader("userId") Integer userId) {

		Auth0 auth0 = new Auth0();
		auth0.setId(userId);
		auth0.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = new Post();
		post.setContent(postReq.getContent());
		post.setUserId(userId);
		post.setLikes(0);

		postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	@DeleteMapping(path = "/{postId}")
	public @ResponseBody ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId,
			@RequestHeader("auth_token") String authToken, @RequestHeader("userId") Integer userId) {

		Auth0 auth0 = new Auth0();
		auth0.setId(userId);
		auth0.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		postRepository.deleteById(postId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted post");
	}

	@PutMapping(path = "/{postId}/like")
	public @ResponseBody ResponseEntity<String> likePost(@PathVariable("postId") Integer postId,
			@RequestHeader("auth_token") String authToken, @RequestHeader("userId") Integer userId) {

		Auth0 auth0 = new Auth0();
		auth0.setId(userId);
		auth0.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		post.setLikes(post.getLikes() + 1);
		postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK).body("Liked post");
	}

	@PutMapping(path = "/{postId}/update")
	public @ResponseBody ResponseEntity<String> updatePost(@PathVariable("postId") Integer postId,
			@RequestBody Post postReq, @RequestHeader("auth_token") String authToken,
			@RequestHeader("userId") Integer userId) {

		Auth0 auth0 = new Auth0();
		auth0.setId(userId);
		auth0.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		post.setContent(postReq.getContent());
		postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK).body("Updated post");
	}
}
