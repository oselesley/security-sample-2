package com.sq009.springsecuritydemo.services.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sq009.springsecuritydemo.models.User;
import com.sq009.springsecuritydemo.repositories.UserRepository;
import com.sq009.springsecuritydemo.services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "user not found!";
	private final UserRepository userRespository;
	private final PasswordEncoder encoder;

	@Autowired
	public UserServiceImpl(final UserRepository userRespository, final PasswordEncoder encoder) {
		this.userRespository = userRespository;
		this.encoder = encoder;
	}

	@Override
	public List<User> fetchAllUsers() {
		return userRespository.findAll();
	}

	@Override
	public void createUser(final User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRespository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Optional<User> userDetailsOptional = userRespository.findUserByUsername(username);
		return userDetailsOptional.orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
	}
}
