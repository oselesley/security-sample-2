package com.sq009.springsecuritydemo.services;

import java.util.List;

import com.sq009.springsecuritydemo.models.User;

public interface UserService {
	void createUser(User user);

	List<User> fetchAllUsers();
}
