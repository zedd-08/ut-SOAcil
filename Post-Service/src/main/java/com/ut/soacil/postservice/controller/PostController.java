package com.ut.soacil.postservice.controller;

import com.ut.soacil.postservice.models.Post;
import com.ut.soacil.postservice.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@DeleteMapping(path="/delete")
	public @ResponseBody String deletePost(@RequestParam Integer postId) {
		postRepository.deleteById(postId);
		return("Deleted");
	}
}
