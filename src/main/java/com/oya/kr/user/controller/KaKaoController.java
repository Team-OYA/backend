package com.oya.kr.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.user.controller.dto.request.AccessTokenRequest;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
import com.oya.kr.user.service.KaKaoLoginService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.15
 */
@RestController
@RequiredArgsConstructor
public class KaKaoController {

	private final KaKaoLoginService kaKaoLoginService;

	/**
	 * 카카오 로그인
	 *
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.02.15
	 */
	@PostMapping("/login/kakao")
	public ResponseEntity<ApplicationResponse<JwtTokenResponse>> loginKakao(
		@RequestBody AccessTokenRequest accessToken) {
		return ResponseEntity.ok(ApplicationResponse.success(kaKaoLoginService.accessTokenLogin(accessToken)));
	}
}
