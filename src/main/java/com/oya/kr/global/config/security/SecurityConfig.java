package com.oya.kr.global.config.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.oya.kr.global.config.AuthInterceptor;
import com.oya.kr.global.jwt.TokenProvider;
import com.oya.kr.global.repository.RedisRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.13
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;
	private final RedisRepository redisRepository;

	/**
	 * Spring Security가 인증 및 권한 검사를 수행하지 않도록 설정
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@Override
	public void configure(WebSecurity web){
		web.ignoring()
			.antMatchers("/resources/**")
			.antMatchers("/css/**")
			.antMatchers("/vendor/**")
			.antMatchers("/js/**")
			.antMatchers("/favicon*/**")
			.antMatchers("/img/**");
	}

	/**
	 * 스프링 시큐리티 규칙
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable()
			.logout().disable();

		// 세션을 사용하지 않기 때문에 STATELESS로 설정
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// 헤더 확인할 JWT filter 적용
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.authorizeRequests()
			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
			.antMatchers("/ws/**", "/chat/**", "/api/v1/chat/**").permitAll()
			.antMatchers("/api/v1/join", "/api/v1/login", "/api/v1/oauth/login").permitAll()
			.anyRequest().authenticated();
	}

	/**
	 * TokenAuthenticationFilter 빈 등록
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(tokenProvider, redisRepository);
	}

	/**
	 * BCryptPasswordEncoder 객체 빈 등록
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
