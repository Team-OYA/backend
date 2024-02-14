package com.oya.kr.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oya.kr.global.config.RootConfig;

/**
 * @author 김유빈
 * @since 2024.02.13
 */
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)
public class SpringApplicationTest {
}
