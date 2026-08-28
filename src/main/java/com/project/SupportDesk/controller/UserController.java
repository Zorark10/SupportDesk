package com.project.SupportDesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.SupportDesk.model.User;

import com.project.SupportDesk.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping("/user")
	public User addUser(@RequestBody User user) {
		return service.addUser(user);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user/{id}")
	public User findById(@PathVariable Integer id) throws Exception {
		return service.findById(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user")
	public List<User> getAllUsers(){
		return service.getAllUser();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/delete/{id}")
	public void deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
	}
	
	@PatchMapping("/user/update/{id}")
	public User updateUser(@PathVariable Integer id, @RequestParam String name, @RequestParam String email) throws Exception {
		return service.updateUser(id, name, email);
	}
}
