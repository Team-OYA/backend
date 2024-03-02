package com.oya.kr.chat.mapper.dto.request;

import com.oya.kr.chat.controller.dto.request.ChatMessageRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateMessageMapperRequest {
	private final Long chatRoomId;
	private final String content;
	// private final Long sender;

	public CreateMessageMapperRequest(ChatMessageRequest message) {
		this.chatRoomId = message.getRoomId();
		this.content = message.getMessage();
	}
}
