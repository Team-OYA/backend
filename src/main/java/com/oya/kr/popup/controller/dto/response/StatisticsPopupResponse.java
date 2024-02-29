package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.mapper.dto.response.StatisticsPopupMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsPopupResponse {

    private final int total;
    private final int ad;

    public static StatisticsPopupResponse from(StatisticsPopupMapperResponse popups) {
        return new StatisticsPopupResponse(
            popups.getTotal(),
            popups.getAd()
        );
    }
}
