package com.oya.kr.user.domain;

public enum RegistrationType {

    BASIC("basic"),
    KAKAO("kakao"),
    ;

    private final String name;

    RegistrationType(String name) {
        this.name = name;
    }
}
