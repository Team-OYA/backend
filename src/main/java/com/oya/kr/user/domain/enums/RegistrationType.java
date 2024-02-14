package com.oya.kr.user.domain.enums;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import com.oya.kr.global.exception.ApplicationException;

public enum RegistrationType {

	BASIC("basic"),
	KAKAO("kakao"),
	;

	private final String name;

	RegistrationType(String name) {
		this.name = name;
	}

	public static RegistrationType findByName(String name) {
		for (RegistrationType enumValue : RegistrationType.values()) {
			if (enumValue.name.equals(name)) {
				return enumValue;
			}
		}
		throw new ApplicationException(WRONG_ENUM);
	}
}
