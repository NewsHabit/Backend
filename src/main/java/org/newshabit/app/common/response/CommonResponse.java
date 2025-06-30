package org.newshabit.app.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
	private String status;
	private String message;
	private T data;

	public static <T> CommonResponse<T> success() {
		return new CommonResponse<>("SUCCESS", "Success", null);
	}

	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>("SUCCESS", "Success", data);
	}

	public static <T> CommonResponse<T> error(String status, String message) {
		return new CommonResponse<>(status, message, null);
	}
}
