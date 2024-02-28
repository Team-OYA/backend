package com.oya.kr.chat.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateChatRoomMapperRequest {

	private final String name;

	// private final Long sender;
	// private final Long receiver;
}
