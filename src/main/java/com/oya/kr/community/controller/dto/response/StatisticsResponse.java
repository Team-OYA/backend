package com.oya.kr.community.controller.dto.response;

import java.util.List;

import com.oya.kr.community.mapper.dto.response.StatisticsResponseMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.20
 */
@Getter
@RequiredArgsConstructor
public class StatisticsResponse {

	private final List<StatisticsDetailResponse> statisticsDetails;
}
