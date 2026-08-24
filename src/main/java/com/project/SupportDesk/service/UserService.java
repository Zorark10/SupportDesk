package com.project.SupportDesk.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.SupportDesk.exception.UserNotFoundException;
import com.project.SupportDesk.model.Role;
import com.project.SupportDesk.model.User;
import com.project.SupportDesk.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {
		user.setRole(Role.USER);
		String passString = user.getPassword();
		String encodedPass = passwordEncoder.encode(passString);
		user.setPassword(encodedPass);
		
		return repo.save(user);
	}
	
	public User findById(Integer id) throws UserNotFoundException {
		User user = repo.findById(id).orElse(null);
		
		if(user == null) {
			throw new UserNotFoundException("User not found");
		}
		return user;
	}
	
	public List<User> getAllUser(){
		return repo.findAll();
	}
	
	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}
	
	public User updateUser(Integer id, String name, String email) throws Exception {
		User currentUser = findById(id);
		
		currentUser.setName(name);
		currentUser.setEmail(email);
		return repo.save(currentUser);
	}
	
}
