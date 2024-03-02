package com.oya.kr.chat.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatListResponse {

	private final int sum;
	private final List<ChatRoomDetailResponse> list;
}
