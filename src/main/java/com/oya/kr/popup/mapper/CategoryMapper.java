package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.CategoryRequest;
import com.oya.kr.popup.mapper.dto.response.CategoryResponse;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
public interface CategoryMapper {

    Optional<CategoryResponse> findByName(String name);

    List<CategoryResponse> findAll();

    void save(CategoryRequest category);

    void deleteAll();
}
