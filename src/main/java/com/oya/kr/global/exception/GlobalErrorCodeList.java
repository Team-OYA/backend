package com.oya.kr.global.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GlobalErrorCodeList implements ErrorCode {

	// token
	INVALID_TOKEN("G0001", HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
	EXPIRE_TOKEN("G0002", HttpStatus.FORBIDDEN, "TOKEN이 만료되었습니다."),
	UNSUPPORTED_TOKEN("G0003", HttpStatus.FORBIDDEN, "지원되지 않는 JWT TOKEN입니다."),
	WRONG_TOKEN("G0004", HttpStatus.FORBIDDEN, "잘못 설계된 TOKEN입니다."),
	WRONG_ENUM("G0005", HttpStatus.FORBIDDEN, "존재하지 않는 ENUM입니다."),
	EXPIRE_REFRESH_TOKEN("G0006", HttpStatus.FORBIDDEN, "REFRESH TOKEN이 만료되었습니다. 다시 로그인해주세요."),

	// s3
	FAIL_CONVERT_S3_IMAGE("G0006", HttpStatus.BAD_REQUEST, "S3 이미지 파일 변환에 실패하였습니다."),

	// date
	CLOSE_DATE_IS_NOT_AFTER_OPEN_DATE("G0007", HttpStatus.BAD_REQUEST, "종료 일자가 시작 일자보다 이후여야 합니다."),

	// regex
	INVALID_FORMAT_CONTACT("G0008", HttpStatus.BAD_REQUEST, "연락처 형식이 잘못되었습니다."),

	// rest template
	HTTP_CLIENT_ERROR("G0009", HttpStatus.BAD_REQUEST, "외부 API 와의 연동에 실패하였습니다. 입력값을 확인해주세요."),
	HTTP_SERVER_ERROR("G0010", HttpStatus.BAD_REQUEST, "외부 API 서버가 불안정하여 연동에 실패하였습니다. 다시 시도해주세요."),
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
