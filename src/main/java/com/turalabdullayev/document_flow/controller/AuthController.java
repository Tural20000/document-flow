package com.turalabdullayev.document_flow.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turalabdullayev.document_flow.entity.Role;
import com.turalabdullayev.document_flow.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
		String token = authenticationService.register(request.get("username"), request.get("email"),
				request.get("password"), Role.valueOf(request.get("role")));
		return ResponseEntity.ok(Map.of("token", token));

	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
		String token = authenticationService.login(request.get("username"), request.get("password"));
		return ResponseEntity.ok(Map.of("token", token));

	}

}
