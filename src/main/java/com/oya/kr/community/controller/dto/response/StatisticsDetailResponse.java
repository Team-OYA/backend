package com.oya.kr.community.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsDetailResponse {

	private final String categoryCode;
	private final int count;
}
