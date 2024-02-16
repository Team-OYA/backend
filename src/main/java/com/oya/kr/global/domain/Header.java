package com.oya.kr.global.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Header {
	AUTH("Authorization"),
	BEARER("Bearer ");
	private final String value;
}
