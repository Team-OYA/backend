package com.oya.kr.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.oya.kr.user.controller.dto.request.KakaoTokenRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuthService {

	public TokenResponse loginKakao(KakaoTokenRequest accessToken) {
		return null;
	}
}
