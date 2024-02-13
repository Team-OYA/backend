package com.oya.kr.user.domain.enums;

import org.apache.ibatis.type.MappedTypes;

import com.oya.kr.global.util.CodeEnum;
import com.oya.kr.global.util.CodeEnumTypeHandler;

public enum Gender implements CodeEnum  {

    MAN("man"),
    WOMAN("woman"),
    ;

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    @MappedTypes(Gender.class)
    public static class TypeHandler extends CodeEnumTypeHandler<Gender> {
        public TypeHandler() {
            super(Gender.class);
        }
    }

    @Override
    public String getCode() {
        return name;
    }

    public static Gender getGenderEnum(int code) {
        switch (code) {
            case 0:
                return Gender.MAN;
            case 1:
                return Gender.WOMAN;
        }
        return null;
    }

}
