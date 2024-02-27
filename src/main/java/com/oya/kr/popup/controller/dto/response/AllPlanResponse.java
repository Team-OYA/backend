package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.DepartmentBranch;
import com.oya.kr.popup.domain.enums.DepartmentFloor;
import com.oya.kr.popup.domain.enums.EntranceStatus;
import com.oya.kr.popup.mapper.dto.response.AllPlanMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AllPlanResponse {

    private final long id;
    private final String companyName;
    private final String office;
    private final String floor;
    private final String openDate;
    private final String closeDate;
    private final String entranceStatus;
    private final String category;
    private final String createdDate;

    public static AllPlanResponse from(AllPlanMapperResponse response) {
        return new AllPlanResponse(
            response.getId(),
            response.getCompanyName(),
            DepartmentBranch.from(response.getOffice()).getDescription(),
            DepartmentFloor.from(response.getFloor()).getDescription(),
            DateConvertor.convertDateFormatForResponse(response.getOpenDate()),
            DateConvertor.convertDateFormatForResponse(response.getCloseDate()),
            EntranceStatus.from(response.getEntranceStatus()).getDescription(),
            Category.from(response.getCategory()).getDescription(),
            DateConvertor.convertDateFormatForResponse(response.getCreatedDate())
        );
    }
}
