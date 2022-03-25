package com.ut.soacil.postservice.controller;

import com.ut.soacil.postservice.models.Post;
import com.ut.soacil.postservice.repository.PostRepository;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/posts")
public class PostController {
	@Autowired
	private PostRepository postRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@PostMapping(path = "/add")
	public @ResponseBody Post addNewPost(@RequestBody Post postReq) {
		Post post = new Post();
		post.setContent(postReq.getContent());
		post.setUserId(postReq.getUserId());
		post.setLikes(0);

		postRepository.save(post);
		return post;
	}

	@DeleteMapping(path = "/{postId}")
	public @ResponseBody ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId) {
		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		postRepository.deleteById(postId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted post");
	}

	@PutMapping(path = "/{postId}/like")
	public @ResponseBody ResponseEntity<String> likePost(@PathVariable("postId") Integer postId) {
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
			@RequestBody Post postReq) {
		Post post = postRepository.findById(postId).orElse(null);
		if (null == post) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
		}
		post.setContent(postReq.getContent());
		postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK).body("Updated post");
	}
}
