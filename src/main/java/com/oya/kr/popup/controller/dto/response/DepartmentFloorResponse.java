package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.domain.enums.DepartmentFloor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DepartmentFloorResponse {

    private final String code;
    private final String description;

    public static DepartmentFloorResponse from(DepartmentFloor floor) {
        return new DepartmentFloorResponse(
            floor.getCode(),
            floor.getDescription()
        );
    }
}
