package com.oya.kr.popup.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DepartmentFloorsWithCategoriesResponse {

    private final List<DepartmentCategoryResponse> categories;
}
