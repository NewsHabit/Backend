package com.gto.newsHabit.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	/** Common **/
	BAD_REQUEST(400, "CM001", "BAD REQUEST"),
	BAD_PARAMETERS(400, "CM002", "INVALID PARAMETERS"),
	PARAMETER_REQUIRED(400, "CM003", "PARAMETER REQUIRED"),
	NOT_FOUND(404, "CM004", "NOT FOUND"),
	BIND_ERROR(400, "CM0005", "BIND ERROR"),
	METHOD_NOT_ALLOWED(405, "CM006", "METHOD NOT ALLOWED"),
	INTERNAL_SERVER_ERR(500, "CM007", "INTERNAL SERVER ERROR");

	private final int status;
	private final String errCode;
	private String message;

	public void setMessage(String msg) {
		this.message = msg;
	}
}
