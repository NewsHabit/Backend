package org.newshabit.app.user.exception;


import lombok.Getter;
import org.newshabit.app.common.exception.BaseException;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicatedException extends BaseException {
	public DuplicatedException(ErrorMessage message) {
		super(message);
	}
	@Getter
	public enum ErrorMessage implements Message {
		USER_ALREADY_EXIST("user already exists", HttpStatus.CONFLICT);

		private final String message;
		private final HttpStatus status;

		ErrorMessage(String message, HttpStatus status) {
			this.message = message;
			this.status = status;
		}
	}
}
