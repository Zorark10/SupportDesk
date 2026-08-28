package com.project.SupportDesk.controller;

import java.net.http.HttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.SupportDesk.model.LoginRequest;
import com.project.SupportDesk.model.LoginResponse;
import com.project.SupportDesk.service.JwtService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/auth")
	public ResponseEntity<LoginResponse> request(@RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		try {
		Authentication auth = authManager.authenticate(token);
		String tokenString = jwtService.generateToken(auth);
		return ResponseEntity.ok(new LoginResponse(tokenString));
		} catch(AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
		}
	}
}
