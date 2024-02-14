package com.oya.kr.user.controller.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinResponse {
	private final long userId;
	private final String email;
}
