package com.security.demo.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.security.demo.annotation.LogAspect;
import com.security.demo.dto.LoggerDTO;
import com.security.demo.utils.StringLiterals;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AspectLoggerConfig {

	private static final Logger logger = LoggerFactory.getLogger(AspectLoggerConfig.class);
	private static final Set<String> SENSITIVE_FIELDS = Set.of("password", "token");
	private static final Set<String> SENSITIVE_TYPES = Set.of("USER_CREATE", "PASSWORD_UPDATE","LOGIN");

	private final HttpServletRequest httpServletRequest;

	public AspectLoggerConfig(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	@Around("@annotation(com.security.demo.annotation.LogAspect)")
	public ResponseEntity<?> aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Method method = getMethod(proceedingJoinPoint);
		ResponseEntity<?> response = null;
		String auditType = getAuditType(method);
		String userName = getUserName(auditType);
		String id = UUID.randomUUID().toString();

		long startTime = System.currentTimeMillis();
		long endTime;
		long executionTime;
		try {

			response = (ResponseEntity<?>) proceedingJoinPoint.proceed();
			endTime = System.currentTimeMillis();
			executionTime = endTime - startTime;
			logExecution(id, userName, auditType, executionTime, proceedingJoinPoint, response);

		} catch (Exception e) {

			logException(id, userName, auditType, proceedingJoinPoint, e.getMessage());

			throw e;
		}
		return response;

	}

	private void logExecution(String id, String userName, String auditType, long executionTime,
			ProceedingJoinPoint proceedingJoinPoint, ResponseEntity<?> response) {
		LoggerDTO loggerDTO = LoggerDTO.builder().UUID(id).userName(userName).auditType(auditType)
				.classAndMethod(proceedingJoinPoint.getSignature().toShortString())
				.methodType(httpServletRequest.getMethod()).uri(httpServletRequest.getRequestURI())
				.ipAddress(httpServletRequest.getRemoteAddr())
				.requestBody(SENSITIVE_TYPES.contains(auditType) ? filterSensitiveData(proceedingJoinPoint.getArgs())
						: Arrays.asList(proceedingJoinPoint.getArgs()))
				.params(SENSITIVE_TYPES.contains(auditType)
						? filterSensitiveParameters(httpServletRequest.getParameterMap())
						: getRequestParam(httpServletRequest.getParameterMap()))
				.responseStatus(Objects.isNull(response) ? "N/A" : response.getStatusCode().toString())
				.executionTime(executionTime)
				.response(SENSITIVE_TYPES.contains(auditType) ? filterSensitiveData(response.getBody())
						: response.getBody())
				.build();

		logger.info(loggerDTO.toString());
	}

	private void logException(String id, String userName, String auditType, ProceedingJoinPoint proceedingJoinPoint,
			String exceptionMsg) {
		LoggerDTO loggerDTO = LoggerDTO.builder().UUID(id).userName(userName).auditType(auditType)
				.classAndMethod(proceedingJoinPoint.getSignature().toShortString())
				.methodType(httpServletRequest.getMethod()).uri(httpServletRequest.getRequestURI())
				.ipAddress(httpServletRequest.getRemoteAddr())
				.requestBody(SENSITIVE_TYPES.contains(auditType) ? filterSensitiveData(proceedingJoinPoint.getArgs())
						: Arrays.asList(proceedingJoinPoint.getArgs()))
				.params(SENSITIVE_TYPES.contains(auditType)
						? filterSensitiveParameters(httpServletRequest.getParameterMap())
						: getRequestParam(httpServletRequest.getParameterMap()))
				.userName(userName).exceptionMsg(exceptionMsg).build();
		logger.error(loggerDTO.toString());
	}

	private String getUserName(String auditType) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (StringLiterals.USER_CREATE.equals(auditType)) {
			userName = httpServletRequest.getParameter("email");
		}
		return userName;
	}

	private String getAuditType(Method method) {
		if (Objects.nonNull(method)) {
			return method.getAnnotation(LogAspect.class).type();
		}
		return "";
	}

	private Method getMethod(ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Class<?> targetClass = joinPoint.getTarget().getClass();
		try {
			return targetClass.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		} catch (Exception e) {
			logger.error("Exception " + e);
			return null;
		}
	}

	private List<Object> filterSensitiveData(Object[] args) {
		return Arrays.asList(args).stream().map(arg -> filterSensitiveData(arg)).collect(Collectors.toList());
	}

	private Object filterSensitiveData(Object targetObject) {
		// Handle generic object
		Object modifiedObject = null;
		try {
			Class<?> targetClass = targetObject.getClass();
			Constructor<?> targetConstructor = targetClass.getDeclaredConstructor();
			targetConstructor.setAccessible(true); // In case the constructor is not public
			modifiedObject = targetConstructor.newInstance();

			for (Field field : targetClass.getDeclaredFields()) {

				field.setAccessible(true);
				Object value = field.get(targetObject);
				// Modify the value if necessary
				if (SENSITIVE_FIELDS.contains(field.getName().toLowerCase())) {
					value = "****";
				} else if (Objects.nonNull(value) && Object.class.equals(field.getType())) {
					value = filterSensitiveData(value);
				}

				// Set the modified value on the new object
				field.set(modifiedObject, value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modifiedObject;
	}

	private Map<String, String> filterSensitiveParameters(Map<String, String[]> parameters) {
		return parameters.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
				entry -> filterSensitiveParameterValues(entry.getKey(), entry.getValue())));

	}

	private String filterSensitiveParameterValues(String key, String[] values) {
		boolean isSensitiveKey = SENSITIVE_FIELDS.contains(key.toLowerCase());
		return Arrays.asList(values).stream().map(value -> isSensitiveKey ? "****" : value).collect(Collectors.toList())
				.toString();

	}

	private Map<String, String> getRequestParam(Map<String, String[]> reqParam) {

		return reqParam.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(", ", entry.getValue())));
	}

}
