package com.oya.kr.chat.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatRoomAndMessageResponse {

	private final ChatRoomDetailResponse chatRoomDetailResponse;
	private final List<ChatMessageDetailResponse> chatMessageDetailResponse;
}
