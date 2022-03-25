package com.ut.user.controller;

import java.util.ArrayList;

import com.ut.user.models.User;
import com.ut.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/{userId}/friends")
	public @ResponseBody Iterable<Integer> getUserFriends(@PathVariable("userId") Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		return user.getFriends();
	}

	@PostMapping(path = "/add")
	public @ResponseBody User createNewUser(@RequestBody User user) {
		User userToAdd = new User();
		userToAdd.setBio(user.getBio());
		userToAdd.setFirstName(user.getFirstName());
		userToAdd.setLastName(user.getLastName());
		userToAdd.setPassword(user.getPassword());
		userToAdd.setFriends(new ArrayList<Integer>());
		userRepository.save(userToAdd);
		return userToAdd;
	}
}
