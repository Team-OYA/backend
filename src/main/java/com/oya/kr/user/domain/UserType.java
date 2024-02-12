package com.oya.kr.user.domain;

public enum UserType {

    USER("user"),
    BUSINESS("business"),
    ADMINISTRATOR("administrator"),
    ;

    private final String name;

    UserType(String name) {
        this.name = name;
    }
}
