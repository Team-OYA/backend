package com.oya.kr.popup.controller.dto.response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.oya.kr.popup.domain.enums.Category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoriesResponse {

    private final List<CategoryResponse> categories;

    public static CategoriesResponse from(Category[] categories) {
        return new CategoriesResponse(
            Arrays.stream(categories)
                .map(CategoryResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
