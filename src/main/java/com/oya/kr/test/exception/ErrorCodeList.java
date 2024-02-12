package com.oya.kr.test.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCodeList implements ErrorCode {
	ERROR_TEST("T0001", HttpStatus.BAD_REQUEST, "에러 테스트입니다."),
	;

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
