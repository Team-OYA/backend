package com.oya.kr.popup.mapper.dto.request;

import com.oya.kr.popup.domain.Plan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanUpdateEntranceStatusMapperRequest {

    private final long id;
    private final String entranceStatus;

    public static PlanUpdateEntranceStatusMapperRequest from(Plan plan) {
        return new PlanUpdateEntranceStatusMapperRequest(plan.getId(), plan.getEntranceStatus().getName());
    }
}
