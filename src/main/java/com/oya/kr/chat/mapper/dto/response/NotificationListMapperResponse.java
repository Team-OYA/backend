package com.oya.kr.chat.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationListMapperResponse {

	private final Long id;
	private final Long userId;
	private final String token;
	private final String userType;
}
