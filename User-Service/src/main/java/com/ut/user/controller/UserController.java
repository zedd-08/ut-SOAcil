package com.ut.user.controller;

import com.ut.user.models.User;
import com.ut.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

@CrossOrigin("*")
@RestController
@RequestMapping(
		path = {"/v1/users"},
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/{userId}/friends")
	public @ResponseBody Iterable<Integer> getUserFriends(@PathVariable("userId") Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		return user == null ? Collections.emptyList() : user.getFriends();
	}

	@PostMapping(path = "/add")
	public @ResponseBody User createNewUser(@RequestBody User user) {
		User userToAdd = new User();
		userToAdd.setBio(user.getBio());
		userToAdd.setFirstName(user.getFirstName());
		userToAdd.setLastName(user.getLastName());
		userToAdd.setPassword(user.getPassword());
		userToAdd.setFriends(new ArrayList<>());
		userRepository.save(userToAdd);
		return userToAdd;
	}
}
