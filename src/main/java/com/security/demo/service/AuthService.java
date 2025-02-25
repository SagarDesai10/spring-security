package com.security.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.security.demo.dto.LoginDTO;
import com.security.demo.dto.MessageResponseDTO;
import com.security.demo.dto.UserRegisterDTO;
import com.security.demo.dto.UserResponseDTO;

import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.validation.Valid;

public interface AuthService {

	UserResponseDTO registerUser(UserRegisterDTO userRegister);

	MessageResponseDTO loginUser(LoginDTO loginRequest) throws InvalidKeyException, JsonProcessingException;

}
