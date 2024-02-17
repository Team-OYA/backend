package com.oya.kr.popup.mapper.dto.request;

import java.time.LocalDate;

import com.oya.kr.popup.domain.Plan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanSaveMapperRequest {

    private final long userId;
    private final String office;
    private final String floor;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final String businessPlanUrl;
    private final String entranceStatus;
    private final String contactInformation;
    private final String category;

    public static PlanSaveMapperRequest from(Plan plan) {
        return new PlanSaveMapperRequest(
            plan.getUser().getId(),
            plan.getDepartmentBranch().getCode(),
            plan.getFloor().getCode(),
            plan.getOpenDate(),
            plan.getCloseDate(),
            plan.getBusinessPlanUrl(),
            plan.getEntranceStatus().getName(),
            plan.getContactInformation(),
            plan.getCategory().getCode()
        );
    }
}
