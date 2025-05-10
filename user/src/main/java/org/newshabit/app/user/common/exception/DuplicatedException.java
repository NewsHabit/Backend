package org.newshabit.app.user.common.exception;

import lombok.Getter;
import org.newshabit.app.common.exception.BaseException;

@Getter
public class DuplicatedException extends BaseException {
	public DuplicatedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
