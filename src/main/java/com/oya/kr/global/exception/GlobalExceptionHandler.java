package com.oya.kr.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oya.kr.global.dto.response.ApplicationResponse;

import lombok.extern.java.Log;

@Log
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String INTERNAL_SERVER_ERROR_CODE = "S0001";

	// 어플리케이션 exception 처리 추가
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApplicationResponse<Void>> handleApplicationException(ApplicationException e) {
		HttpStatus statusCode = e.getStatusCode();
		ApplicationResponse<Void> response = ApplicationResponse.fail(e.getCode(), e.getMessage());
		return ResponseEntity.status(statusCode).body(response);
	}

	// spring validation exception 처리 추가
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApplicationResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
		ApplicationResponse<Void> response = ApplicationResponse.fail(INTERNAL_SERVER_ERROR_CODE,
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return ResponseEntity.badRequest().body(response);
	}

	// global exception 처리 추가
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApplicationResponse<Void>> handleGlobalException(Exception e) {
		log.info(e.getMessage());
		ApplicationResponse<Void> response = ApplicationResponse.fail(INTERNAL_SERVER_ERROR_CODE, e.getMessage());
		return ResponseEntity.badRequest().body(response);
	}
}
