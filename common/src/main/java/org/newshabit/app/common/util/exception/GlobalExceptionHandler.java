package org.newshabit.app.common.util.exception;

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
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<CommonResponse<Object>> handleUnknownRuntime(RuntimeException e) {
		log.error("Unhandled RuntimeException: {}", e.getMessage(), e);
		CommonResponse<Object> errorResponse = new CommonResponse<>(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			e.getMessage(),
			LocalDateTime.now()
		);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(errorResponse);
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<CommonResponse<Object>> handleBaseException(BaseException e) {
		log.warn("{} called: {}", e.getClass().getSimpleName(), e.getMessage());
		CommonResponse<Object> errorResponse = new CommonResponse<>(
			e.getStatus().value(),
			e.getMessage(),
			LocalDateTime.now()
		);
		return ResponseEntity
			.status(e.getStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<Object>> handleException(Exception e) {
		CommonResponse<Object> errorResponse = new CommonResponse<>(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"INTERNAL SERVER ERROR",
			LocalDateTime.now()
		);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(errorResponse);
	}
}
