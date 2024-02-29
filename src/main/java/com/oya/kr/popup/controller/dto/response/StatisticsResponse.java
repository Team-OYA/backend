package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.mapper.dto.response.StatisticsCommunityMapperResponse;
import com.oya.kr.popup.mapper.dto.response.StatisticsPlanMapperResponse;
import com.oya.kr.popup.mapper.dto.response.StatisticsPopupMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsResponse {

    private final StatisticsPlanResponse plan;
    private final StatisticsPopupResponse popup;
    private final StatisticsCommunityResponse community;

    public static StatisticsResponse from(StatisticsPlanMapperResponse plans, StatisticsPopupMapperResponse popups, StatisticsCommunityMapperResponse communities) {
        return new StatisticsResponse(
            StatisticsPlanResponse.from(plans),
            StatisticsPopupResponse.from(popups),
            StatisticsCommunityResponse.from(communities)
        );
    }
}
