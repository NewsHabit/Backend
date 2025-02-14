package org.newshabit.app.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
	private int status;
	private String message;
	private T data;
	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>(200, "Success", data);
	}

	// 실패 응답을 위한 정적 메서드
	public static <T> CommonResponse<T> error(int status, String message) {
		return new CommonResponse<>(status, message, null);
	}
}
