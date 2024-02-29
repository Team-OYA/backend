package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.mapper.dto.response.StatisticsPlanMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsPlanResponse {

    private final int total;
    private final int request;
    private final int waiting;
    private final int approval;
    private final int rejection;
    private final int withdrawal;

    public static StatisticsPlanResponse from(StatisticsPlanMapperResponse plans) {
        return new StatisticsPlanResponse(
            plans.getTotal(),
            plans.getRequest(),
            plans.getWaiting(),
            plans.getApproval(),
            plans.getRejection(),
            plans.getWithdrawal()
        );
    }
}
