package com.oya.kr.popup.repository;

import org.springframework.stereotype.Repository;

import com.oya.kr.popup.mapper.CategoryMapper;
import com.oya.kr.popup.mapper.dto.request.CategoryMapperRequest;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.21
 */
@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryMapper categoryMapper;

    /**
     * repository 계층으로 분리
     *
     * @parameter String
     * @author 김유빈
     * @since 2024.02.21
     */
    public void save(String category) {
        CategoryMapperRequest request = new CategoryMapperRequest(category);
        if (categoryMapper.findByName(request.getName()).isEmpty()) {
            categoryMapper.save(request);
        }
    }
}
