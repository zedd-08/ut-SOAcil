package com.ut.soacil.postservice.controller;

import com.ut.soacil.postservice.models.Auth0Body;
import com.ut.soacil.postservice.models.NotificationBody;
import com.ut.soacil.postservice.models.Post;
import com.ut.soacil.postservice.repository.PostRepository;
import com.ut.soacil.postservice.utils.AuthenticateUser;
import com.ut.soacil.postservice.utils.NotificationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = { "/v1/post-service" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
	@Autowired
	private PostRepository postRepository;

	@GetMapping(path = "/all")
	@Operation(summary = "Get all posts.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Post.class)))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Iterable<Post>> getAllPosts() {
		return ResponseEntity.ok(postRepository.findAll());
	}

	@GetMapping(path = "/{post_id}")
	@Operation(summary = "Get post by id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Post.class))
			}),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Post> getPostById(@PathVariable("post_id") Integer postId) {
		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(post);
	}

	@PostMapping(path = "/add")
	@Operation(summary = "Create and add a new post.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Post.class))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<Post> addNewPost(@RequestBody Post postReq,
			@RequestHeader("auth_token") String authToken, @RequestHeader("user_id") Integer userId) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = new Post();
		post.setContent(postReq.getContent());
		post.setUser_id(userId);
		post.setLikes(0);

		postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	@DeleteMapping(path = "/{post_id}")
	@Operation(summary = "Get post by id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<String> deletePost(@PathVariable("post_id") Integer postId,
			@RequestHeader("auth_token") String authToken, @RequestHeader("user_id") Integer userId) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		postRepository.deleteById(postId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted post");
	}

	@PutMapping(path = "/{post_id}/like")
	@Operation(summary = "Adds a like to the given post from the user specified on the message body.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<String> likePost(@PathVariable("post_id") Integer postId,
			@RequestHeader("auth_token") String authToken, @RequestHeader("user_id") Integer userId) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		Integer to_user_id = post.getUser_id();
		post.setLikes(post.getLikes() + 1);
		postRepository.save(post);

		NotificationBody notificationBody = new NotificationBody(to_user_id, userId, "Someone liked your post!");
		NotificationHandler.sendNotification(notificationBody);
		return ResponseEntity.status(HttpStatus.OK).body("Liked post");
	}

	@PutMapping(path = "/{post_id}/update")
	@Operation(summary = "Updates an edited post.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
			}),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Error, issue with the application.")
	})
	public @ResponseBody ResponseEntity<String> updatePost(@PathVariable("post_id") Integer postId,
			@RequestBody Post postReq, @RequestHeader("auth_token") String authToken,
			@RequestHeader("user_id") Integer userId) {

		Auth0Body auth0Body = new Auth0Body();
		auth0Body.setUser_id(userId);
		auth0Body.setAuth_token(authToken);
		if (!AuthenticateUser.isAuthValid(auth0Body)) {
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
