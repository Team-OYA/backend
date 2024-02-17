package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDate;

import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.domain.DepartmentBranch;
import com.oya.kr.popup.domain.DepartmentFloor;
import com.oya.kr.popup.domain.EntranceStatus;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanResponse {

    private final long id;
    private final String office;
    private final String floor;
    private final String location;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final String businessPlanUrl;
    private final String entranceStatus;
    private final String contactInformation;
    private final String category;

    public Plan toDomain(User user) {
        return new Plan(
            id, user, DepartmentBranch.from(office), DepartmentFloor.from(floor), location, openDate, closeDate,
            businessPlanUrl, EntranceStatus.from(entranceStatus), contactInformation, Category.from(category)
        );
    }
}
