package org.newshabit.app.common.util;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.util.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CommonResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
		log.warn("handleIllegalArgumentException called {}", e.getMessage());
		CommonResponse<Object> errorResponse = new CommonResponse<>(
			HttpStatus.BAD_REQUEST.value(),
			e.getMessage(),
			LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<Object>> handleException(Exception e) {
		CommonResponse<Object> errorResponse = new CommonResponse<>(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"INTERNAL SERVER ERROR",
			LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
