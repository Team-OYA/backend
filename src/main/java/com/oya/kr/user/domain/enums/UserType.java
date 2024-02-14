package com.oya.kr.user.domain.enums;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import com.oya.kr.global.exception.ApplicationException;

public enum UserType {

	USER("user"),
	BUSINESS("business"),
	ADMINISTRATOR("administrator"),
	;

	private final String name;

	UserType(String name) {
		this.name = name;
	}

	public static UserType getUserTypeEnum(int code) {
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

	public static UserType findByName(String name) {
		for (UserType enumValue : UserType.values()) {
			if (enumValue.name.equals(name)) {
				return enumValue;
			}
		}
		throw new ApplicationException(WRONG_ENUM);
	}
}
