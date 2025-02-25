package com.security.demo.dto;

import com.security.demo.enums.Roles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

	private String id;
	private String name;
	private String email;
	private Roles role;
}
