package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.mapper.dto.response.StatisticsCommunityMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsCommunityResponse {

    private final int total;
    private final int ad;

    public static StatisticsCommunityResponse from(StatisticsCommunityMapperResponse communities) {
        return new StatisticsCommunityResponse(
            communities.getTotal(),
            communities.getAd()
        );
    }
}
