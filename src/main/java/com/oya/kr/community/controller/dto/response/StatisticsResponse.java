package com.oya.kr.community.controller.dto.response;

import java.util.List;

import com.oya.kr.community.mapper.dto.response.StatisticsResponseMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsResponse {

	private final List<StatisticsDetailResponse> statisticsDetails;
}
