package com.oya.kr.user.domain.enums;

import org.apache.ibatis.type.MappedTypes;

import com.oya.kr.global.util.CodeEnum;
import com.oya.kr.global.util.CodeEnumTypeHandler;

public enum UserType implements CodeEnum {

    USER("user"),
    BUSINESS("business"),
    ADMINISTRATOR("administrator"),
    ;

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    @MappedTypes(UserType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<UserType> {
        public TypeHandler() {
            super(UserType.class);
        }
    }

    @Override
    public String getCode() {
        return name;
    }

    public static UserType getUserTypeEnum(int code){
        switch (code) {
            case 0:
                return UserType.USER;
            case 1:
                return UserType.BUSINESS;
            case 2:
                return UserType.ADMINISTRATOR;
        }
        return null;
    }
}
