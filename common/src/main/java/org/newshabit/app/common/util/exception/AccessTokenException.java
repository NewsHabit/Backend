package org.newshabit.app.common.util.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccessTokenException extends BaseException {
	public AccessTokenException(ErrorMessage message) {
		super(message);
	}
	@Getter
	public enum ErrorMessage implements Message {
		EXPIRED_TOKEN("access token has expired.", HttpStatus.UNAUTHORIZED),
		INVALID_TOKEN("invalid access token.", HttpStatus.UNAUTHORIZED),
		MISSING_TOKEN("Access token is missing.", HttpStatus.UNAUTHORIZED);

		private final String message;
		private final HttpStatus status;

		ErrorMessage(String message, HttpStatus status) {
			this.message = message;
			this.status = status;
		}
	}
}
