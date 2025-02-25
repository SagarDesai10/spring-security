package com.security.demo.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MessageResponseDTO {

	private String message;
	private String token;
}
