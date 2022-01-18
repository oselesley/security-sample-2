package com.sq009.springsecuritydemo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sq009.springsecuritydemo.models.User;
import com.sq009.springsecuritydemo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/get-users")
	public ResponseEntity<List<User>> fetchAllUsers () {
		List<User> userList = userService.fetchAllUsers();

		return new ResponseEntity(userList, HttpStatus.OK);
	}
}
