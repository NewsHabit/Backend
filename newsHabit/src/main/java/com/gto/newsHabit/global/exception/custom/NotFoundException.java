package com.gto.newsHabit.global.exception.custom;

import com.gto.newsHabit.global.exception.ErrorCode;

public class NotFoundException extends CustomRuntimeException {
	private ErrorCode errorCode;

	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
