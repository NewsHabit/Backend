package org.newshabit.app.user.common.exception;

import lombok.Getter;
import org.newshabit.app.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements BaseErrorCode {
	DUPLICATED_USER("DUPLICATED_USER", "user already exists", HttpStatus.CONFLICT),
	USER_NOT_FOUND("USER_NOT_FOUND", "user not found.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorCode(String errorCode, String message, HttpStatus status) {
		this.code = errorCode;
		this.message = message;
		this.httpStatus = status;
	}
}
