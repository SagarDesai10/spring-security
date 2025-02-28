package com.security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.security.demo.annotation.LogAspect;
import com.security.demo.controller.common.BaseController;
import com.security.demo.dto.LoginDTO;
import com.security.demo.dto.UserRegisterDTO;
import com.security.demo.dto.response.SuccessResponse;
import com.security.demo.service.AuthService;
import com.security.demo.utils.StringLiterals;

import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController<Object> {

	@Autowired
	private AuthService authService;
	
	@LogAspect(type=StringLiterals.USER_CREATE)
	@PostMapping("/register")
	public ResponseEntity<SuccessResponse<Object>> registerUser(@Valid @RequestBody UserRegisterDTO userRegister)
	{
		return success(authService.registerUser(userRegister),StringLiterals.OK,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<Object>> loginUser(@Valid @RequestBody LoginDTO loginRequest) throws InvalidKeyException, JsonProcessingException
	{
		return success(authService.loginUser(loginRequest),StringLiterals.OK,HttpStatus.OK);
	}
}
