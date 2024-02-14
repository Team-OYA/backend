package com.oya.kr.popup.controller.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.oya.kr.popup.domain.Department;
import com.oya.kr.popup.domain.DepartmentBranch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DepartmentResponse {

    private final String code;
    private final String description;
    private final List<DepartmentBranchResponse> branches;

    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(
            department.getCode(),
            department.getDescription(),
            DepartmentBranch.findAllByDepartment(department).stream()
                .map(DepartmentBranchResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
