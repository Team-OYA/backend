package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.domain.Category;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
public interface CategoryMapper {

    Optional<Category> findByName(String name);

    List<Category> findAll();

    void save(Category category);

    void deleteAll();
}
