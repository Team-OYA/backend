package com.oya.kr.chat.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.oya.kr.chat.controller.dto.request.ChatMessageRequest;
import com.oya.kr.chat.service.ChatService;
import com.oya.kr.global.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.28
 */
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WebSocketController {

	private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;
	private final TokenProvider tokenProvider;

	/**
	 * 메시지 전송
	 *
	 * @author 이상민
	 * @since 2024.02.28
	 */
	@MessageMapping("/sendMessage")
	@SendTo("/topic/messages")
	public ChatMessageRequest sendMessage(ChatMessageRequest chatMessageRequest,
		@Header("Authorization") String authorizationHeader) {

		String token = extractToken(authorizationHeader);
		if (token == null) {
			return null;
		}
		Long userId = tokenProvider.getUserId(token);

		// 메시지를 채팅방에 저장
		chatService.saveMessage(chatMessageRequest, userId);

		// 이전 메시지를 클라이언트로 전송
		List<ChatMessageRequest> previousMessages = chatService.findByChatRoomId(chatMessageRequest.getRoomId(), userId);
		for (ChatMessageRequest previousMessage : previousMessages) {
			messagingTemplate.convertAndSend("/topic/messages/" + chatMessageRequest.getRoomId(), previousMessage);
		}
		return chatMessageRequest;
	}

	/**
	 * 헤더에서 토큰을 추출
	 *
	 * @author 이상민
	 * @since 2024.03.03
	 */
	private String extractToken(String authorizationHeader) {
		// "Bearer " 다음의 토큰 부분만 추출
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}

}