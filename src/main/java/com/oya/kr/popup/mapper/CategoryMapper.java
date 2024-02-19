package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.CategoryMapperRequest;
import com.oya.kr.popup.mapper.dto.response.CategoryMapperResponse;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
public interface CategoryMapper {

	Optional<CategoryMapperResponse> findByName(String name);

	List<CategoryMapperResponse> findAll();

	void save(CategoryMapperRequest category);

	void deleteAll();
}
