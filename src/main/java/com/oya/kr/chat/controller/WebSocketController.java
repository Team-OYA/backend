package com.oya.kr.chat.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.oya.kr.chat.controller.dto.ChatMessageRequest;
import com.oya.kr.chat.domain.ChatMessage;
import com.oya.kr.chat.repository.ChatMessageRepository;
import com.oya.kr.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

	private final ChatService chatService;

	@MessageMapping("/sendMessage")
	@SendTo("/topic/messages")
	public ChatMessageRequest sendMessage(ChatMessageRequest chatMessageRequest) {
		// 메시지를 채팅방에 저장
		chatService.saveMessage(chatMessageRequest);
		// // 채팅방의 모든 메시지를 조회하여 전송
		// List<ChatMessageRequest> messages = chatService.findByChatRoomId(chatMessageRequest.getRoomId());
		return chatMessageRequest;
	}
}