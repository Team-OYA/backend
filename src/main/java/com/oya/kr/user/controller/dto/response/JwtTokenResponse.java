package com.oya.kr.user.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenResponse {
	private final String grantType;  // Bearer
	private final String accessToken;
	private final String refreshToken;
}
