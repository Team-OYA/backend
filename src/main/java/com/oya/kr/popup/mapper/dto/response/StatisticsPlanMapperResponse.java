package com.oya.kr.popup.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticsPlanMapperResponse {

    private final int total;
    private final int request;
    private final int waiting;
    private final int approval;
    private final int rejection;
    private final int withdrawal;
}
