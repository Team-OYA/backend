package com.oya.kr.popup.controller.dto.response;

import java.time.LocalDate;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.domain.Plan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanResponse {

    private final DepartmentBranchResponse office;
    private final DepartmentFloorResponse floor;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final CategoryResponse category;

    public static PlanResponse from(Plan plan) {
        return new PlanResponse(
            DepartmentBranchResponse.from(plan.getDepartmentBranch()),
            DepartmentFloorResponse.from(plan.getFloor()),
            plan.getOpenDate(), plan.getCloseDate(),
            CategoryResponse.from(plan.getCategory())
        );
    }

    public String getOpenDate() {
        return DateConvertor.convertDateFormatForResponse(openDate);
    }

    public String getCloseDate() {
        return DateConvertor.convertDateFormatForResponse(closeDate);
    }
}
