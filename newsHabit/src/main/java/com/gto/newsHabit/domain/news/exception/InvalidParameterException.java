package com.gto.newsHabit.domain.news.exception;

import com.gto.newsHabit.global.exception.ErrorCode;
import com.gto.newsHabit.global.exception.custom.CustomRuntimeException;

public class InvalidParameterException extends CustomRuntimeException {
	public InvalidParameterException(ErrorCode errorCode) {
		super(errorCode);
	}
}
