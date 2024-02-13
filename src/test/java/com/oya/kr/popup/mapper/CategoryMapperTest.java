package com.oya.kr.popup.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.popup.domain.Category;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
class CategoryMapperTest extends SpringApplicationTest {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 테스트 전 데이터 초기화
     *
     * @author 김유빈
     * @since 2024.02.13
     */
    @BeforeEach
    void setUp() {
        categoryMapper.deleteAll();
    }

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
     * findByName 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.13
     */
    @DisplayName("이름으로 카테고리를 조회한다")
    @Test
    void findByName() {
        // given
        Category category = Category.FOOD;
        categoryMapper.save(category);

        // when
        Optional<Category> savedCategory = categoryMapper.findByName(category.name());

        // then
        assertThat(savedCategory).isPresent();
    }

    /**
     * save 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.13
     */
    @DisplayName("카테고리를 저장한다")
    @Test
    void save() {
        // given
        Category category = Category.FOOD;

        // when & then
        assertThatCode(() -> categoryMapper.save(category))
            .doesNotThrowAnyException();
    }
}
