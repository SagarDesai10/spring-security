package com.security.demo.dto;

import com.security.demo.enums.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

	@Email
	@NotEmpty(message="Email should not be null or empty")
	private String email;

	@NotEmpty(message="Password should not be null or empty")
	private String password;
	
}
