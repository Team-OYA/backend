package com.oya.kr.popup.controller.dto.response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.DepartmentFloor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DepartmentCategoryResponse {

    private final String code;
    private final String description;
    private final List<DepartmentFloorResponse> floors;

    public static DepartmentCategoryResponse from(Category category) {
        return new DepartmentCategoryResponse(
            category.getCode(),
            category.getDescription(),
            Arrays.stream(DepartmentFloor.values())
                .map(DepartmentFloorResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
