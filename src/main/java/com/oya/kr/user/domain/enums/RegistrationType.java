package com.oya.kr.user.domain.enums;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

@Getter
public enum RegistrationType {

	BASIC("basic"),
	KAKAO("kakao"),
	;

	private final String name;

	RegistrationType(String name) {
		this.name = name;
	}

	public static RegistrationType findByName(String name) {
		return Arrays.stream(RegistrationType.values())
			.filter(enumValue -> enumValue.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ApplicationException(WRONG_ENUM));
	}
}
