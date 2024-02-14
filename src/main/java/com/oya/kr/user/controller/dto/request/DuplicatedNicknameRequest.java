package com.oya.kr.user.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicatedNicknameRequest {

	private final String nickname;

	private final boolean flag;
}
