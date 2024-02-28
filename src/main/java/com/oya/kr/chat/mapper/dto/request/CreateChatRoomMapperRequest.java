package com.oya.kr.chat.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateChatRoomMapperRequest {

	private final String name; // 채팅 방 이름
	private final Long sender; // creator
}
