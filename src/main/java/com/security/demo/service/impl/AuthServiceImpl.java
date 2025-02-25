package com.security.demo.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.security.demo.dto.LoginDTO;
import com.security.demo.dto.MessageResponseDTO;
import com.security.demo.dto.UserRegisterDTO;
import com.security.demo.dto.UserResponseDTO;
import com.security.demo.entity.User;
import com.security.demo.jwt.JwtTokenProvider;
import com.security.demo.mapper.UserMapper;
import com.security.demo.repository.UserRepository;
import com.security.demo.service.AuthService;

import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserResponseDTO registerUser(UserRegisterDTO userRegister) {
		
		Optional<User> optUser=userRepo.findByEmail(userRegister.getEmail().toLowerCase());
		
		if(optUser.isPresent())
		{
			throw new RuntimeException("Email already present");
			
		}
		
		User user=User.builder().name(userRegister.getName())
				.email(userRegister.getEmail().toLowerCase())
				.role(userRegister.getRole())
				.password(passwordEncoder.encode(userRegister.getPassword()))
				.createdAt(Timestamp.from(Instant.now())).build();//store time in utc zone
		
		return userMapper.mapToDTO(userRepo.save(user));

	}

	@Override
	public MessageResponseDTO loginUser(LoginDTO loginRequest) throws InvalidKeyException, JsonProcessingException {

		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail().toLowerCase(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		
		return MessageResponseDTO.builder().token(token).build();
	}

}
