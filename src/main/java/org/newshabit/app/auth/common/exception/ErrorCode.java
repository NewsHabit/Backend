package org.newshabit.app.auth.common.exception;

import lombok.Getter;
import org.newshabit.app.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements BaseErrorCode {
	EXPIRED_TOKEN("EXPIRED_TOKEN", "access token has expired.", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN("INVALID_TOKEN", "invalid access token.", HttpStatus.UNAUTHORIZED),
	AUTH_ROLE_MISSING_TOKEN("AUTH_ROLE_MISSING_TOKEN", "user role is missing.", HttpStatus.UNAUTHORIZED),
	NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "refresh token not found.", HttpStatus.UNAUTHORIZED);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorCode(String errorCode, String message, HttpStatus status) {
		this.code = errorCode;
		this.message = message;
		this.httpStatus = status;
	}
}
