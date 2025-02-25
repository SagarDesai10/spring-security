package com.security.demo.controller.common;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.security.demo.dto.response.ErrorResponse;
import com.security.demo.dto.response.SuccessResponse;


public class BaseController<T> {

	protected ResponseEntity<SuccessResponse<T>> success(T data, String message, HttpStatus httpStatus) {
		SuccessResponse<T> successResponse = new SuccessResponse<T>();
		successResponse.setData(data);
		successResponse.setMessage(message);
		successResponse.setStatusCode(httpStatus.value());
		return new ResponseEntity<>(successResponse, httpStatus);
	}

	protected ResponseEntity<ErrorResponse<T>> error(T message, HttpStatus httpStatus, String path) {
		ErrorResponse<T> errorResponse = new ErrorResponse<T>();
		errorResponse.setTimeStamp(new Date());
		errorResponse.setMessage(message);
		errorResponse.setStatusCode(httpStatus.value());
		errorResponse.setPath(path);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}
}
