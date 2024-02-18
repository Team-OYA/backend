package com.oya.kr.popup.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PopupErrorCodeList implements ErrorCode {

    NOT_EXIST_POPUP_CATEGORY("PU0001", HttpStatus.BAD_REQUEST, "존재하지 않는 팝업스토어 카테고리입니다."),
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
