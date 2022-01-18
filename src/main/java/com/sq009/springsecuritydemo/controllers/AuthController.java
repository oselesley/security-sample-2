package com.sq009.springsecuritydemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sq009.springsecuritydemo.dtos.AuthResponseDto;
import com.sq009.springsecuritydemo.dtos.LoginDto;
import com.sq009.springsecuritydemo.filters.JWTUtil;
import com.sq009.springsecuritydemo.models.User;
import com.sq009.springsecuritydemo.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;

	public AuthController(final UserDetailsService userDetailsService, final UserService userService,
		final AuthenticationManager authenticationManager, final JWTUtil jwtUtil) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> login (@RequestBody LoginDto dto) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
		} catch (Exception e) {
			throw new Exception("incorrect username or password!");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		final AuthResponseDto authResponseDto = new AuthResponseDto();
		authResponseDto.setToken(jwt);
		return new ResponseEntity(authResponseDto, HttpStatus.CREATED);
	}

	@PostMapping("/users")
	public ResponseEntity<String> createUser (@RequestBody final User user) {
		userService.createUser(user);

		return new ResponseEntity("User created", HttpStatus.CREATED);
	}
}
