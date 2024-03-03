package com.oya.kr.chat.controller.dto.request;

import com.oya.kr.chat.controller.dto.response.ChatMessageDetailResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ChatMessageRequest {

	private Long roomId;
	private String sender;
	private String message;
	private String sendingTime;

	public ChatMessageRequest(ChatMessageDetailResponse detail,Long roomId) {
		this.roomId = roomId;
		this.sender = detail.getSenderNickname();
		this.message = detail.getMessage();
		this.sendingTime = detail.getSendingTime();
	}
}