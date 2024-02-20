package com.gto.newsHabit.global.exception.custom;

import com.gto.newsHabit.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
	private ErrorCode errorCode;

	public CustomRuntimeException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
