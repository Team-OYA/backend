package com.oya.kr.popup.config;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.mapper.CategoryMapper;
import com.oya.kr.popup.mapper.dto.response.CategoryMapperResponse;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
class InitializingCategoryConfigTest extends SpringApplicationTest {

	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * 테스트 후 데이터 초기화
	 *
	 * @author 김유빈
	 * @since 2024.02.13
	 */
	@AfterEach
	void init() {
		categoryMapper.deleteAll();
	}

	/**
	 * 팝업스토어 카테고리 정보 초기화 테스트 작성
	 *
	 * @author 김유빈
	 * @since 2024.02.13
	 */
	@DisplayName("어플리케이션 실행 시 카테고리를 저장한다")
	@Test
	void initCategory() {
		// given
		List<CategoryMapperResponse> categories = categoryMapper.findAll();

		// when & then
		assertThat(categories.size()).isEqualTo(Category.values().length);
	}
}
