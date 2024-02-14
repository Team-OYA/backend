package com.oya.kr.popup.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.mapper.CategoryMapper;
import com.oya.kr.popup.mapper.dto.request.CategoryRequest;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
@Configuration
@RequiredArgsConstructor
public class InitializingCategoryConfig implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryMapper categoryMapper;

	/**
	 * 어플리케이션 실행 시 팝업스토어 카테고리 정보 초기화
	 *
	 * @parameter ContextRefreshedEvent
	 * @author 김유빈
	 * @since 2024.02.13
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		for (Category category : Category.values()) {
			CategoryRequest request = new CategoryRequest(category.getName());
			if (categoryMapper.findByName(request.getName()).isEmpty()) {
				categoryMapper.save(request);
			}
		}
	}
}
