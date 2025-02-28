package com.security.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoggerDTO {

	private String UUID;
	private String userName;
	private String auditType;
	private String methodType;
	private String uri;
	private String ipAddress;
	private String classAndMethod;
	private List<Object> requestBody;
	private Map<String, String> params;
	private String responseStatus;
	private Long executionTime;
	private String exceptionMsg;
	private Object response;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Audit Log [UUID:").append(UUID).append("]: Method Call:").append(classAndMethod)
				.append(" Executed by user: ").append(userName).append(" from IP: ").append(ipAddress)
				.append(" | Audit Type: ").append(auditType).append(" | URI:").append(uri).append(" | Request Body: ")
				.append(requestBody).append(" | Request Parameters: ").append(params).append(" | Response Status: ")
				.append(responseStatus).append(" | Execution Time: ").append(executionTime).append(" | Response Body: ")
				.append(response).append(" | Exception Message: ").append(exceptionMsg);

		return builder.toString();

	}

}
