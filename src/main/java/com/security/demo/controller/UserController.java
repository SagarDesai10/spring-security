package com.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.controller.common.BaseController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController<Object> {

	@GetMapping("/hello")
	public String helloFromUser()
	{
		return "hello from user";
	}
}
