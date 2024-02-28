package com.oya.kr.chat.controller.dto;

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

	public ChatMessageRequest(ChatMessageDetailResponse detail) {
		this.roomId = 1L;
		this.sender = null;
		this.message = detail.getMessage();
		this.sendingTime = detail.getSendingTime();
	}

	// //메시지 타입 :  입장 채팅
	// public enum MessageType{
	// 	ENTER, TALK, JOIN
	// }
	// private MessageType type; //메시지 타입
	// private String roomId;// 방 번호
	// private String sender;//채팅을 보낸 사람
	// private String message;// 메세지
}