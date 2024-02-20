package com.oya.kr.community.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.20
 */
@Getter
@RequiredArgsConstructor
public class StatisticsDetailResponse {

	private final String categoryCode;
	private final int count;
}
