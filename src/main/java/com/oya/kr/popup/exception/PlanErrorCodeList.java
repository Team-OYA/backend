package com.oya.kr.popup.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlanErrorCodeList implements ErrorCode {

    NOT_BUSINESS("PN0001", HttpStatus.BAD_REQUEST, "사업체가 아닙니다."),
    NOT_EXIST_DEPARTMENT_BRANCH("PN0002", HttpStatus.BAD_REQUEST, "존재하지 않는 지점입니다."),
    NOT_EXIST_DEPARTMENT_FLOOR("PN0003", HttpStatus.BAD_REQUEST, "존재하지 않는 층수입니다."),
    NOT_EXIST_PLAN("PN0004", HttpStatus.BAD_REQUEST, "존재하지 않는 사업계획서 입니다."),
    NOT_EXIST_ENTRANCE_STATUS("PN0005", HttpStatus.BAD_REQUEST, "존재하지 않는 사업계획서 상태 입니다."),
    NOT_ENTRANCE_STATUS_IS_REQUEST("PN0006", HttpStatus.BAD_REQUEST, "입점 요청 상태가 아닙니다."),
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
