package org.newshabit.app.user.common.exception;

import lombok.Getter;
import org.newshabit.app.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements BaseErrorCode {
	DUPLICATED_USER("DUPLICATED_USER", "이미 존재하는 유저입니다.", HttpStatus.CONFLICT),
	DAILY_GOAL_DUPLICATED("DAILY_GOAL_DUPLICATED", "이전 목표와 동일합니다.", HttpStatus.CONFLICT),
	DAILY_GOAL_TOO_FAST("DAILY_GOAL_TOO_FAST", "변경 불가 기간입니다.", HttpStatus.BAD_REQUEST),
	USER_NOT_FOUND("USER_NOT_FOUND", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorCode(String errorCode, String message, HttpStatus status) {
		this.code = errorCode;
		this.message = message;
		this.httpStatus = status;
	}
}
