	package com.security.demo.dto;

import com.security.demo.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class UserRegisterDTO {

	@NotEmpty(message="Name should not be null or empty")
	private String name;
	
	@Email
	@NotEmpty(message="Email should not be null or empty")
	private String email;
	
	@NotNull(message="Role should not be null or empty")
	private Roles role;
	
	@Size(min=6,max=12,message="Password length must be between 6 to 12")
	private String password;
}
