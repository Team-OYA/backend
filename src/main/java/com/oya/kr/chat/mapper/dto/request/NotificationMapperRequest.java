package com.oya.kr.chat.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationMapperRequest {

	private final Long userId;
	private final String token;
}
