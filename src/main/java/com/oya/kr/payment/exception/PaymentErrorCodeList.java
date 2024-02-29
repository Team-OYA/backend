package com.oya.kr.payment.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
@RequiredArgsConstructor
public enum PaymentErrorCodeList implements ErrorCode {

    FAIL("PY0001", HttpStatus.BAD_REQUEST, "결제에 실패하였습니다."),
    ALREADY_PAY("PY0002", HttpStatus.BAD_REQUEST, "이미 결제된 주문 내역입니다."),
    NOT_EQUAL_AMOUNT("PY0003", HttpStatus.BAD_REQUEST, "광고 금액과 결제 금액이 상이합니다."),
    INVALID_ORDER_ID("PY0004", HttpStatus.BAD_REQUEST, "잘못된 주문 아이디입니다."),
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
