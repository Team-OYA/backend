package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.domain.enums.DepartmentBranch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DepartmentBranchResponse {

    private final String code;
    private final String description;

    public static DepartmentBranchResponse from(DepartmentBranch branch) {
        return new DepartmentBranchResponse(
            branch.getCode(),
            branch.getDescription()
        );
    }
}
