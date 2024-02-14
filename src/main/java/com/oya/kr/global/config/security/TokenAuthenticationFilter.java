package com.oya.kr.global.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.12
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";
	private final TokenProvider tokenProvider;

	/**
	 * security 필터
	 *
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
		if (!isAuthenticationRequired(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String token = getAccessToken(authorizationHeader);
			if (tokenProvider.validToken(token)) {
				Authentication authentication = tokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (ApplicationException e) {
			// log.info("Application exception in TokenAuthenticationFilter: {}", e.getMessage());
			// 사용자 정의 에러 응답 객체 생성
			ApplicationResponse<Void> errorResponse = ApplicationResponse.fail(e.getCode(), e.getMessage());
			response.setStatus(e.getStatusCode().value());
			response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
		}
	}

	/**
	 * header에서 accessToken 얻기
	 *
	 * @author 이상민
	 * @since 2024.02.12
	 */
	private String getAccessToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			return authorizationHeader.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	/**
	 * security 적용안되는 url 찾기
	 *
	 * @author 이상민
	 * @since 2024.02.12
	 */
	private boolean isAuthenticationRequired(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return !("/api/v1/login".equals(requestURI) || "/api/v1/join".equals(requestURI));
	}
}
