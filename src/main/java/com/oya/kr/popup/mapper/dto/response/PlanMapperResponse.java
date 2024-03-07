package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDate;

import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.DepartmentBranch;
import com.oya.kr.popup.domain.enums.DepartmentFloor;
import com.oya.kr.popup.domain.enums.EntranceStatus;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanMapperResponse {

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
    private final long userId;

    public Plan toDomain() {
        return createPlan(null);
    }

    public Plan toDomain(User user) {
        return createPlan(user);
    }

    private Plan createPlan(User user) {
        return new Plan(
            id, user, DepartmentBranch.from(office), DepartmentFloor.from(floor), location, openDate, closeDate,
            businessPlanUrl, EntranceStatus.from(entranceStatus), contactInformation, Category.from(category)
        );
    }
}
