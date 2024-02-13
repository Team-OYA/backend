package com.oya.kr.global.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GlobalErrorCodeList implements ErrorCode {

	// token
	INVALID_TOKEN("G0001",HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
	EXPIRE_TOKEN("G0002",HttpStatus.FORBIDDEN, "TOKEN이 만료되었습니다."),
	UNSUPPORTED_TOKEN("G0003",HttpStatus.FORBIDDEN, "지원되지 않는 JWT TOKEN입니다."),
	WRONG_TOKEN("G0004",HttpStatus.FORBIDDEN, "잘못 설계된 TOKEN입니다.")
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
