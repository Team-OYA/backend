package com.oya.kr.popup.domain.enums;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_ENTRANCE_STATUS;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

@Getter
public enum EntranceStatus {

	ALL("", "전체 조회"),
	REQUEST("request", "입점 요청"),
	WAITING("waiting", "입점 대기"),
	WITHDRAWAL("withdrawal", "입점 철회"),
	APPROVAL("approval", "입점 승인"),
	REJECTION("rejection", "입점 거절"),
	;

	private final String name;
	private final String description;

	EntranceStatus(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * code 를 이용하여 적절한 EntranceStatus 객체 조회
	 *
	 * @parameter String
	 * @return EntranceStatus
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public static EntranceStatus from(String entranceStatus) {
		return Arrays.stream(values())
			.filter(it -> it.name.equals(entranceStatus))
			.findFirst()
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_ENTRANCE_STATUS));
	}

	/**
	 * 입점 요청 상태 여부 반환
	 *
	 * @return boolean
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public boolean isRequest() {
		return this == REQUEST;
	}

	/**
	 * 입점 대기 상태 여부 반환
	 *
	 * @return boolean
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public boolean isWaiting() {
		return this == WAITING;
	}

	/**
	 * 입점 승인 상태 여부 반환
	 *
	 * @return boolean
	 * @author 김유빈
	 * @since 2024.02.19
	 */
	public boolean isApprove() {
		return this == APPROVAL;
	}
}
