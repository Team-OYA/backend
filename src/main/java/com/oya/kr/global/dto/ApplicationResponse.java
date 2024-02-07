package com.oya.kr.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApplicationResponse<T> {

	private final String code;
	private final String message;
	private final T data;

	public static <T> ApplicationResponse<T> success(T data) {
		return new ApplicationResponse<>(null, null, data);
	}

	public static <T> ApplicationResponse<T> fail(String code, String message) {
		return new ApplicationResponse<>(code, message, null);
	}
}
