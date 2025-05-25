package org.newshabit.app.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements BaseErrorCode {
	COMMON_SERVER_ERROR("COMMON_SERVER_ERROR", "SERVER ERROR :(", HttpStatus.INTERNAL_SERVER_ERROR),
	COMMON_BAD_REQUEST("COMMON_BAD_REQUEST", "BAD REQUEST :(", HttpStatus.BAD_REQUEST),
	COMMON_UNAUTHORIZED("COMMON_UNAUTHORIZED", "UNAUTHORIZED :(", HttpStatus.UNAUTHORIZED),
	COMMON_NOT_FOUND("COMMON_NOT_FOUND", "NOT FOUND :(", HttpStatus.NOT_FOUND),
	COMMON_VALIDATION_ERROR("COMMON_VALIDATION_ERROR", "VALIDATION ERROR :(", HttpStatus.BAD_REQUEST);


	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	CommonErrorCode(String errorCode, String message, HttpStatus status) {
		this.code = errorCode;
		this.message = message;
		this.httpStatus = status;
	}
}
