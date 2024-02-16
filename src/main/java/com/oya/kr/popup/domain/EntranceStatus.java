package com.oya.kr.popup.domain;

import lombok.Getter;

@Getter
public enum EntranceStatus {

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
}
