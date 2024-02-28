package com.oya.kr.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.oya.kr.global.config.security.SecurityConfig;

/**
 * @author 이상민
 * @since 2024.02.12
 */
@Configuration
@EnableWebMvc
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer
    implements WebMvcConfigurer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletConfig.class, SecurityConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
	}

	/**
	 * MultipartFile 업로드 크기 제한
	 *
	 * @return MultipartResolver
	 * @author 김유빈
	 * @since 2024.02.17
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(100000); // 최대 업로드 크기 설정
		return resolver;
	}

	/**
	 * API 응답 형식을 기본(xml) 에서 json 으로 변경
	 *
	 * @return MappingJackson2HttpMessageConverter
	 * @author 김유빈
	 * @since 2024.02.14
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}
}

