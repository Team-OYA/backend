package com.oya.kr.popup.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlanErrorCodeList implements ErrorCode {

    NOT_BUSINESS("PN0001", HttpStatus.BAD_REQUEST, "사업체가 아닙니다."),
    NOT_EXIST_DEPARTMENT("PN0002", HttpStatus.BAD_REQUEST, "존재하지 않는 지점입니다."),
    NOT_EXIST_DEPARTMENT_FLOOR("PN0003", HttpStatus.BAD_REQUEST, "존재하지 않는 층수입니다."),
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
