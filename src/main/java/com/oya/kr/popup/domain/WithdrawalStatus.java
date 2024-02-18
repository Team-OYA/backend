package com.oya.kr.popup.domain;

import static com.oya.kr.popup.exception.PopupErrorCodeList.NOT_EXIST_WITHDRAWAL_STATUS;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

@Getter
public enum WithdrawalStatus {

	DO_NOT_APPLY("do not apply", "철회 신청하지 않음"),
	REQUEST("request", "철회 요청"),
	APPROVAL("approval", "철회 승인"),
	REJECTION("rejection", "철회 거절"),
	;

	private final String name;
	private final String description;

	WithdrawalStatus(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * name 를 이용하여 적절한 WithdrawalStatus 객체 조회
	 *
	 * @parameter String
	 * @return WithdrawalStatus
	 * @author 김유빈
	 * @since 2024.02.19
	 */
	public static WithdrawalStatus from(String name) {
		return Arrays.stream(values())
			.filter(it -> it.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_WITHDRAWAL_STATUS));
	}
}
