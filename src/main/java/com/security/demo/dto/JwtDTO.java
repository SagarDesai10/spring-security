package com.security.demo.dto;

import com.security.demo.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDTO {

	private String id;
	private String username;
	private Roles role;
}
