package com.oya.kr.popup.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.mapper.CategoryMapper;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
class InitializingCategoryConfigTest extends SpringApplicationTest {

    @Autowired
    private CategoryMapper categoryMapper;

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
        List<Category> categories = categoryMapper.findAll();

        // when & then
        assertThat(categories.size()).isEqualTo(Category.values().length);
    }
}
