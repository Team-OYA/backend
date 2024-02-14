package com.oya.kr.user.domain.enums;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import com.oya.kr.global.exception.ApplicationException;

public enum Gender  {

    MAN("man"),
    WOMAN("woman"),
    ;

    private final String name;

    Gender(String name) {
        this.name = name;
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

    public static Gender findByName(String name) {
        for (Gender enumValue : Gender.values()) {
            if (enumValue.name.equals(name)) {
                return enumValue;
            }
        }
        throw new ApplicationException(WRONG_ENUM);
    }
}
