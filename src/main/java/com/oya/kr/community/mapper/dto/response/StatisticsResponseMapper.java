package com.oya.kr.community.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsResponseMapper {

	private final String categoryCode;
	private final int count;
}
