package com.security.demo.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

import com.security.demo.controller.common.BaseController;
import com.security.demo.dto.response.ErrorResponse;

import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController<Object> {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse<Object>> handleValidationErrors(MethodArgumentNotValidException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		String error = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList()).get(0);
		return error(error, HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse<Object>> handleAccessDeniedException(AccessDeniedException exception,
			WebRequest webRequest) {
		logger.error("Unauthorized resource : {}", exception.getMessage());
		return error("Unauthorized resource", HttpStatus.UNAUTHORIZED, webRequest.getDescription(false));
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse<Object>> handleAuthenticationException(AuthenticationException exception,
			WebRequest webRequest) {
		logger.error("Bad Authentication : {}", exception.getMessage());
		return error("Bad Authentication", HttpStatus.UNAUTHORIZED, webRequest.getDescription(false));
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse<Object>> handleUnauthorizedException(UnauthorizedException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.UNAUTHORIZED, webRequest.getDescription(false));
	}

	@ExceptionHandler(ResourceAccessException.class)
	public ResponseEntity<ErrorResponse<Object>> handleResourceAccessException(ResourceAccessException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.UNAUTHORIZED, webRequest.getDescription(false));
	}

	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<ErrorResponse<Object>> handleInvalidLoginException(InvalidLoginException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.UNAUTHORIZED, webRequest.getDescription(false));
	}

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ErrorResponse<Object>> handleSignatureException(AuthenticationException exception,
			WebRequest webRequest) {
		logger.error("Invalid token : {}", exception.getMessage());
		return error("Invalid token", HttpStatus.UNAUTHORIZED, webRequest.getDescription(false));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse<Object>> runtimeException(RuntimeException exception, WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(NoSuchMethodException.class)
	public ResponseEntity<ErrorResponse<Object>> noSuchMethodException(RuntimeException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<ErrorResponse<Object>> securityException(RuntimeException exception, WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(InstantiationException.class)
	public ResponseEntity<ErrorResponse<Object>> instantiationException(RuntimeException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse<Object>> illegalArgumentException(RuntimeException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(InvocationTargetException.class)
	public ResponseEntity<ErrorResponse<Object>> invocationTargetException(RuntimeException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<ErrorResponse<Object>> illegalAccessException(RuntimeException exception,
			WebRequest webRequest) {
		logger.error("Exception occured : {}", exception.getMessage());
		return error(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest.getDescription(false));
	}

	
}
