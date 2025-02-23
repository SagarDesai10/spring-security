package com.security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@GetMapping("/hello")
	public ResponseEntity hello()
	{
		return ResponseEntity.ok().body("hello");
	}
}
