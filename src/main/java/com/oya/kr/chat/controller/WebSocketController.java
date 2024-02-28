package com.oya.kr.chat.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.oya.kr.chat.controller.dto.ChatMessageRequest;
import com.oya.kr.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

	private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/sendMessage")
	@SendTo("/topic/messages")
	public ChatMessageRequest sendMessage(ChatMessageRequest chatMessageRequest) {
		// 메시지를 채팅방에 저장
		chatService.saveMessage(chatMessageRequest);

		// 이전 메시지를 클라이언트로 전송
		List<ChatMessageRequest> previousMessages = chatService.findByChatRoomId(chatMessageRequest.getRoomId());
		for (ChatMessageRequest previousMessage : previousMessages) {
			messagingTemplate.convertAndSendToUser(
				chatMessageRequest.getSender(),
				"/topic/messages",
				previousMessage
			);
		}

		return chatMessageRequest;
	}
}