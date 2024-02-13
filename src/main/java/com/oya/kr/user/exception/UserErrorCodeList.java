package com.oya.kr.user.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCodeList implements ErrorCode {

	NOT_EXIST_USER("U0001", HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
	NOT_RESISTER_USER("U0002", HttpStatus.BAD_REQUEST, "가입이 불가능합니다."),
	EXISTENT_EMAIL("U0003",HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
	EXISTENT_NICKNAME("U0004",HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
	INVALID_PASSWORD("U0005",HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_USER("U0006",HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
	NOT_EXISTENT_EMAIL("U0007",HttpStatus.BAD_REQUEST, "가입되지 않은 Email 입니다."),
	NOT_CORRECTED_PASSWORD("U0008",HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
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
