package org.newshabit.app.user.exception;


import lombok.Getter;
import org.newshabit.app.common.exception.BaseException;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends BaseException {
	public NotFoundException(ErrorMessage message) {
		super(message);
	}
	@Getter
	public enum ErrorMessage implements Message {
		USER_NOT_FOUND("access token has expired.", HttpStatus.NOT_FOUND);

		private final String message;
		private final HttpStatus status;

		ErrorMessage(String message, HttpStatus status) {
			this.message = message;
			this.status = status;
		}
	}
}
