package com.gto.newsHabit.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	/** Common **/
	BAD_REQUEST(400, "CM001", "BAD REQUEST"),
	VALID_FAILED(400, "CM002", "Valid Test Failed."),
	BAD_ARGUMENT(400, "CM003", "Wrong Argument"),
	NOT_FOUND(404, "CM004", "NOT FOUND"),
	METHOD_NOT_ALLOWED(405, "CM005", "METHOD NOT ALLOWED"),
	NULL_POINT(500, "CM0006", "NULL POINT EXCEPTION"),
	INTERNAL_SERVER_ERR(500, "CM007", "INTERNAL SERVER ERROR");

	private final int status;
	private final String errCode;
	private String message;

	public void setMessage(String msg) {
		this.message = msg;
	}
}
