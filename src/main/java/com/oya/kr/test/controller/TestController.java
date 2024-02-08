package com.oya.kr.test.controller;

import static com.oya.kr.test.exception.ErrorCodeList.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.global.exception.ApplicationException;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/test")
@Log
public class TestController {

	@GetMapping("/success")
	public ResponseEntity<ApplicationResponse<String>> successTest() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApplicationResponse.success("data"));
	}

	@GetMapping("/error")
	public ResponseEntity<ApplicationResponse<String>> errorTest() {
		throw new ApplicationException(ERROR_TEST);
	}
}
