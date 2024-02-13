package com.oya.kr.user.domain.enums;

import org.apache.ibatis.type.MappedTypes;

import com.oya.kr.global.util.CodeEnum;
import com.oya.kr.global.util.CodeEnumTypeHandler;

public enum RegistrationType implements CodeEnum {

    BASIC("basic"),
    KAKAO("kakao"),
    ;

    private final String name;

    RegistrationType(String name) {
        this.name = name;
    }

    @MappedTypes(RegistrationType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<RegistrationType> {
        public TypeHandler() {
            super(RegistrationType.class);
        }
    }

    @Override
    public String getCode() {
        return name;
    }
}
