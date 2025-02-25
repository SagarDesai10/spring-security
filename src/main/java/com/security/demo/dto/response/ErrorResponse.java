package com.security.demo.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse <T>{
	private Date timeStamp;
    private T message;
    private int statusCode;
    private String path;
}
