package com.oya.kr.user.domain;

public enum Gender {

    MAN("man"),
    WOMAN("woman"),
    ;

    private final String name;

    Gender(String name) {
        this.name = name;
    }
}
