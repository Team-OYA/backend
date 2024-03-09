package com.oya.kr.chat.controller.dto.response;

import com.oya.kr.chat.mapper.dto.response.ChatRoomDetailMapperResponse;
import com.oya.kr.global.domain.DateConvertor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatRoomDetailResponse {

	private final long chatRoomId;
	private final String name;
	private final String createdDate;
	private final String modifiedDate;

	public ChatRoomDetailResponse(ChatRoomDetailMapperResponse response) {
		this.chatRoomId = response.getId();
		this.name = response.getName();
		this.createdDate = DateConvertor.convertDateFormatForResponse(response.getCreatedDate());
		this.modifiedDate = DateConvertor.convertDateFormatForResponse(response.getModifiedDate());
	}
}
