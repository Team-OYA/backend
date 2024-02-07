package com.oya.kr.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String getCode();

	HttpStatus getStatusCode();

	String getMessage();
}
