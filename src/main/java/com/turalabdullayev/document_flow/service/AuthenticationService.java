package com.turalabdullayev.document_flow.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.turalabdullayev.document_flow.config.JwtService;
import com.turalabdullayev.document_flow.entity.Role;
import com.turalabdullayev.document_flow.entity.User;
import com.turalabdullayev.document_flow.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public String register(String username, String email, String password, Role role) {
		User user = User.builder().username(username).email(email).password(passwordEncoder.encode(password)).role(role)
				.build();
		repository.save(user);
		return jwtService.generateToken(user);
	}

	public String login(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		User user = repository.findByUsername(username).orElseThrow();
		return jwtService.generateToken(user);
	}

}
