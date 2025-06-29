package org.newshabit.common.auth.common.exception;

import org.newshabit.app.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class AccessTokenException extends AuthenticationException {
	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;


	public AccessTokenException(BaseErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode.getCode();
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}

	public AccessTokenException(BaseErrorCode errorCode, Throwable t) {
		super(errorCode.getMessage(), t);
		this.errorCode = errorCode.getCode();
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
