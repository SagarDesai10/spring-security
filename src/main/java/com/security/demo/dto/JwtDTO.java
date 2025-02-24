package com.security.demo.dto;

import com.security.demo.enums.Roles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtDTO {

	private String id;
	private String username;
	private Roles role;
}
