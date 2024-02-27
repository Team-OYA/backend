package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.Department;
import com.oya.kr.popup.domain.enums.DepartmentBranch;
import com.oya.kr.popup.domain.enums.DepartmentFloor;
import com.oya.kr.popup.domain.enums.EntranceStatus;
import com.oya.kr.popup.mapper.dto.response.PlanAboutMeMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPlanResponse {

    private final long id;
    private final String office;
    private final String floor;
    private final String openDate;
    private final String closeDate;
    private final String entranceStatus;
    private final String category;
    private final boolean writtenPopup;
    private final String createdDate;

    public static MyPlanResponse from(PlanAboutMeMapperResponse response) {
        return new MyPlanResponse(
            response.getId(),
            DepartmentBranch.from(response.getOffice()).getDescription(),
            DepartmentFloor.from(response.getFloor()).getDescription(),
            DateConvertor.convertDateFormatForResponse(response.getOpenDate()),
            DateConvertor.convertDateFormatForResponse(response.getCloseDate()),
            EntranceStatus.from(response.getEntranceStatus()).getDescription(),
            Category.from(response.getCategory()).getDescription(),
            response.isWrittenPopup(),
            DateConvertor.convertDateFormatForResponse(response.getCreatedDate())
        );
    }
}
