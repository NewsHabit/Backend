package org.newshabit.app.user.common.exception;


import lombok.Getter;
import org.newshabit.app.common.exception.BaseException;

@Getter
public class NotFoundException extends BaseException {
	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
