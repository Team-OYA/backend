package com.oya.kr.global.config;

import static com.oya.kr.global.config.security.TokenAuthenticationFilter.*;
import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import java.lang.annotation.Annotation;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private final TokenProvider tokenProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (CorsUtils.isPreFlightRequest(request)) {
			return true;
		}
		validateToken(request);
		return true;
	}

	private <T extends Annotation> Optional<T> parseAnnotation(HandlerMethod handler, Class<T> clazz) {
		return Optional.ofNullable(handler.getMethodAnnotation(clazz));
	}

	private void validateToken(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("Authorization");
		String accessToken = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			accessToken =  authorizationHeader.substring(7);
		}

		if (tokenProvider.validToken(accessToken)) {
			throw new ApplicationException(INVALID_TOKEN);
		}
	}
}