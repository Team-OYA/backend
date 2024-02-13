package com.oya.kr.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oya.kr.global.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**")
			.antMatchers("/css/**")
			.antMatchers("/vendor/**")
			.antMatchers("/js/**")
			.antMatchers("/favicon*/**")
			.antMatchers("/img/**");
	}

	// 스프링 시큐리티 규칙
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // csrf 보안 설정 비활성화
			.httpBasic().disable()
			.formLogin().disable()
			.logout().disable();

		// 세션을 사용하지 않기 때문에 STATELESS로 설정
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// 헤더 확인할 JWT filter 적용
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.authorizeRequests()
			.antMatchers("/join", "/login", "/").permitAll()
			.antMatchers("/mypage", "/users/reissue").authenticated()
			.anyRequest().permitAll();
	}

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter(){
		return new TokenAuthenticationFilter(tokenProvider);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
