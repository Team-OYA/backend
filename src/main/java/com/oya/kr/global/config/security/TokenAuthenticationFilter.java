package com.oya.kr.global.config.security;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oya.kr.global.dto.response.ApplicationResponse;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;
import com.oya.kr.global.repository.RedisRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.12
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";
	private final TokenProvider tokenProvider;
	private final RedisRepository redisRepository;

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
		logger.info("token : " + authorizationHeader);

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
			logger.error("잘못된 헤더 형식 : {}", authorizationHeader);
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String accessToken = getAccessToken(authorizationHeader);
			if (tokenProvider.validToken(accessToken)) {

				Object getAccessToken = redisRepository.getData(accessToken);
				if(getAccessToken == null){
					throw new ApplicationException(EXPIRE_REFRESH_TOKEN);
				}

				Authentication authentication = tokenProvider.getAuthentication(accessToken);
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

	private static final List<String> PUBLIC_URIS = Arrays.asList(
		"/chat/room",
		"/ws/chat","/ws",
		"/api/v1/login", "/api/v1/join", "/api/v1/oauth/login");

	/**
	 * security 적용안되는 url 찾기
	 *
	 * @author 이상민
	 * @since 2024.02.12
	 */
	private boolean isAuthenticationRequired(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return !PUBLIC_URIS.contains(requestURI);
	}
}
