package com.project.SupportDesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.SupportDesk.model.LoginRequest;

@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	
	@PostMapping("/auth")
	public void request(@RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		Authentication auth = authManager.authenticate(token);
		
	}
}
