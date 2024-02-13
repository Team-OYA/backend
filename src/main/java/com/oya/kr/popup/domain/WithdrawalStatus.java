package com.oya.kr.popup.domain;

public enum WithdrawalStatus {

    DO_NOT_APPLY("do not apply", "철회 신청하지 않음"),
    REQUEST("request", "철회 요청"),
    APPROVAL("approval", "철회 승인"),
    REJECTION("rejection", "철회 거절"),
    ;

    private final String name;
    private final String description;

    WithdrawalStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
