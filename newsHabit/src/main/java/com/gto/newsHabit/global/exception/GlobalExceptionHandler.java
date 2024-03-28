package com.gto.newsHabit.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.gto.newsHabit.global.exception.custom.CustomRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({BindException.class})
	public ResponseEntity<ErrorResponse> validException(BindException ex) {
		log.error("BIND ERROR", ex.getBindingResult().getAllErrors().get(0));
		ErrorCode ec = ErrorCode.BIND_ERROR;
		ec.setMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		ErrorResponse response = new ErrorResponse(ec);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({CustomRuntimeException.class})
	public ResponseEntity<ErrorResponse> customException(CustomRuntimeException ex) {
		log.error(ex.getMessage());
		ErrorCode ec = ex.getErrorCode();
		ec.setMessage(ex.getMessage());
		ErrorResponse response = new ErrorResponse(ec);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler({MissingServletRequestParameterException.class})
	public ResponseEntity<ErrorResponse> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
		log.error(ex.getMessage());
		ErrorCode ec = ErrorCode.PARAMETER_REQUIRED;
		ErrorResponse response = new ErrorResponse(ec);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<ErrorResponse> typeErrorException(MethodArgumentTypeMismatchException ex) {
		log.error("TYPE ERROR", ex.getMessage());
		ErrorCode ec = ErrorCode.BAD_PARAMETERS;
		ErrorResponse response = new ErrorResponse(ec);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<ErrorResponse> runtimeException(RuntimeException ex) {
		log.error("처리되지 않은 에러입니다.", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERR);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	@ExceptionHandler({NoResourceFoundException.class})
	public ResponseEntity<ErrorResponse> NoResourceFoundExceptionHandle(
		NoResourceFoundException ex) {
		log.error("지원하지 않는 메소드 요청입니다.", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("!!!!!! SERVER ERROR !!!!!!", ex.getMessage());
		ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERR);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
}
