package com.oya.kr.global.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
	ERROR_TEST("S001", HttpStatus.BAD_REQUEST, "테스트 에러입니다.");

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public HttpStatus getStatusCode() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
