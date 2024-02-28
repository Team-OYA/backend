package com.oya.kr.chat.controller.dto.response;

import com.oya.kr.chat.mapper.dto.response.ChatRoomDetailMapperResponse;
import com.oya.kr.chat.mapper.dto.response.MessageDetailMapperResponse;
import com.oya.kr.global.domain.DateConvertor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatMessageDetailResponse {

	private final String senderNickname;
	private final String message;
	private final String sendingTime;

	public ChatMessageDetailResponse(MessageDetailMapperResponse message) {
		this.senderNickname = null;
		this.message = message.getContent();
		this.sendingTime = DateConvertor.convertDateFormatForResponse(message.getModifiedDate());
	}
}
