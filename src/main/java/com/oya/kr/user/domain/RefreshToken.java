package com.oya.kr.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshToken {

	private Long id;
	private final long refreshTokenId;
	private final long userId;
	private final String refreshToken;
}
