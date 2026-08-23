package com.project.SupportDesk.security;

import org.springframework.stereotype.Service;

import com.project.SupportDesk.model.User;
import com.project.SupportDesk.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService { 
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByEmail(username);
		if(user == null)
			throw new UsernameNotFoundException("User Not Found");
		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
																.password(user.getPassword()).
																roles(user.getRole().name()).build();
		
	}

}
