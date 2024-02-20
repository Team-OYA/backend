package com.oya.kr.community.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.20
 */
@Getter
@RequiredArgsConstructor
public class StatisticsResponseMapper {

	private final String categoryCode;
	private final int count;
}
