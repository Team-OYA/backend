package com.oya.kr.user.domain.enums;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

@Getter
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
			default:
				return UserType.ADMINISTRATOR;
		}
	}

	/**
	 * name으로 UserType 변환
	 *
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public static UserType from(String name) {
		return Arrays.stream(UserType.values())
			.filter(enumValue -> enumValue.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ApplicationException(WRONG_ENUM));
	}

	/**
	 * 사업체 판별
	 *
	 * @return boolean
	 * @author 김유빈
	 * @since 2024.02.16
	 */
	public boolean isBusiness() {
		return this.name.equals(BUSINESS.name);
	}

	/**
	 * 관리자 판별
	 *
	 * @return boolean
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public boolean isAdministrator() {
		return this.name.equals(ADMINISTRATOR.name);
	}
}
