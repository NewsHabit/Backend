package org.newshabit.common.auth.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class AccessTokenException extends AuthenticationException {

	public AccessTokenException(String msg) {
		super(msg);
	}

	public AccessTokenException(String msg, Throwable t) {
		super(msg, t);
	}

	@Getter
	public enum ErrorMessage {
		EXPIRED_TOKEN("access token has expired.", HttpStatus.UNAUTHORIZED),
		INVALID_TOKEN("invalid access token.", HttpStatus.UNAUTHORIZED),
		AUTH_ROLE_MISSING_TOKEN("user role is missing.", HttpStatus.UNAUTHORIZED);

		private final String message;
		private final HttpStatus status;

		ErrorMessage(String message, HttpStatus status) {
			this.message = message;
			this.status = status;
		}
	}
}
