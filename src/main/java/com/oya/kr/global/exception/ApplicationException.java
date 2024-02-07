package com.oya.kr.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private final String code;
	private final HttpStatus statusCode;
	private final String message;

	public ApplicationException(ErrorCode errorCode) {
		code = errorCode.getCode();
		statusCode = errorCode.getStatusCode();
		message = errorCode.getMessage();
	}
}