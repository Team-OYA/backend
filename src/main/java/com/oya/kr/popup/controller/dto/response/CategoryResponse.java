package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.domain.enums.Category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {

    private final String code;
    private final String description;

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
            category.getCode(),
            category.getDescription()
        );
    }
}
