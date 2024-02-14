package com.oya.kr.user.domain.enums;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

public enum Gender {

	MAN("man"),
	WOMAN("woman"),
	;

	public String getName() {
		return name;
	}

	private final String name;

	Gender(String name) {
		this.name = name;
	}

	public static Gender getGenderEnum(int code) {
		if (code == 0) {
			return Gender.MAN;
		}
		return Gender.WOMAN;
	}

	public static Gender findByName(String name) {
		return Arrays.stream(Gender.values())
			.filter(enumValue -> enumValue.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ApplicationException(WRONG_ENUM));
	}
}
