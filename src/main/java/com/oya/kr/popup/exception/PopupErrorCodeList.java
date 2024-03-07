package com.oya.kr.popup.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PopupErrorCodeList implements ErrorCode {

    NOT_EXIST_POPUP_CATEGORY("PU0001", HttpStatus.BAD_REQUEST, "존재하지 않는 팝업스토어 카테고리입니다."),
    NOT_EXIST_POPUP("PU0002", HttpStatus.BAD_REQUEST, "존재하지 않는 팝업스토어 게시글입니다."),
    NOT_EXIST_WITHDRAWAL_STATUS("PU0003", HttpStatus.BAD_REQUEST, "존재하지 않는 팝업스토어 게시글 철회 상태입니다."),
    NOT_EXIST_POPUP_SORT("PU0004", HttpStatus.BAD_REQUEST, "존재하지 않는 팝업스토어 게시글 정렬 정보입니다."),
    NOT_EXIST_ORDER_ID_FOR_AD("PU0005", HttpStatus.BAD_REQUEST, "존재하지 않는 주문 정보입니다."),
    CHAT_GPT_FAIL("PU0006", HttpStatus.BAD_REQUEST, "팝업스토어 게시글 요약 기능 요청에 실패하였습니다."),
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
