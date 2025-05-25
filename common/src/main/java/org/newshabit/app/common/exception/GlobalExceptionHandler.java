package org.newshabit.app.common.exception;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CommonResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
		log.warn("handleIllegalArgumentException called {}", e.getMessage());

		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_BAD_REQUEST.getCode(),
			CommonErrorCode.COMMON_BAD_REQUEST.getMessage(),
			LocalDateTime.now()
		);
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<CommonResponse<Object>> handleAuthenticationException(AuthenticationException e) {
		log.error("AuthenticationException: {}", e.getMessage());

		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_UNAUTHORIZED.getCode(),
			CommonErrorCode.COMMON_UNAUTHORIZED.getMessage(),
			LocalDateTime.now()
		);

		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CommonResponse<Object>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

		FieldError error = e.getBindingResult().getFieldError();
		String errorMessage = (error != null) ? error.getDefaultMessage() : "입력값이 올바르지 않습니다.";

		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_VALIDATION_ERROR.getCode(),
			errorMessage,
			LocalDateTime.now()
		);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<CommonResponse<Object>> httpMessageNotReadableException(HttpMessageNotReadableException e) {

		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_BAD_REQUEST.getCode(),
			CommonErrorCode.COMMON_BAD_REQUEST.getMessage(),
			LocalDateTime.now()
		);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<CommonResponse<Object>> handleNoResourceFoundException(NoResourceFoundException e) {
		log.error("Unhandled ResourceException: {}", e.getMessage(), e);

		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_NOT_FOUND.getCode(),
			CommonErrorCode.COMMON_NOT_FOUND.getMessage(),
			LocalDateTime.now()
		);

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(errorResponse);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<CommonResponse<Object>> handleUnknownRuntime(RuntimeException e) {
		log.error("Unhandled RuntimeException: {}", e.getMessage(), e);
		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_SERVER_ERROR.getCode(),
			CommonErrorCode.COMMON_SERVER_ERROR.getMessage(),
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
			e.getErrorCode(),
			e.getMessage(),
			LocalDateTime.now()
		);

		return ResponseEntity
			.status(e.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<Object>> handleException(Exception e) {
		log.error("Unhandled Exception: {}", e.getMessage(), e);

		CommonResponse<Object> errorResponse = new CommonResponse<>(
			CommonErrorCode.COMMON_SERVER_ERROR.getCode(),
			CommonErrorCode.COMMON_SERVER_ERROR.getMessage(),
			LocalDateTime.now()
		);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(errorResponse);
	}
}
