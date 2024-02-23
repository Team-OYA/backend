package com.oya.kr.user.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AccessTokenRequest {

	private final String accessToken;
}
