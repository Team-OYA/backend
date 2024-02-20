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
    NOT_EXIST_POPUP("PN0011", HttpStatus.BAD_REQUEST, "존재하지 않는 팝업스토어 게시글 입니다."),
    NOT_ENTRANCE_STATUS_IS_REQUEST("PN0006", HttpStatus.BAD_REQUEST, "입점 요청 상태가 아닙니다."),
    NOT_ENTRANCE_STATUS_IS_WAITING("PN0007", HttpStatus.BAD_REQUEST, "입점 대기 상태가 아닙니다."),
    NOT_ENTRANCE_STATUS_IS_APPROVE("PN0009", HttpStatus.BAD_REQUEST, "입점 승인 상태가 아닙니다."),
    NOT_ENTRANCE_STATUS_IS_REQUEST_OR_WAITING("PN0008", HttpStatus.BAD_REQUEST, "입점 거절이 가능한 상태가 아닙니다."),
    PLAN_HAS_POPUP("PN0010", HttpStatus.BAD_REQUEST, "팝업스토어 게시글이 이미 존재합니다."),
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
