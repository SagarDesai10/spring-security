package com.security.demo.jwt;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.security.demo.entity.User;
import com.security.demo.repository.UserRepository;

@Service
public class CustomUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepo.findByEmail(username.toLowerCase())
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString())));
	}

}
