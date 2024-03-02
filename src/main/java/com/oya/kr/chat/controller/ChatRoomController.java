package com.oya.kr.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oya.kr.chat.controller.dto.response.ChatRoomAndMessageResponse;
import com.oya.kr.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.27
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

	private final ChatService chatService;

	/**
	 * 채팅방 입장하기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	@GetMapping("/room/{roomId}")
	public String showChatRoomDetails(@PathVariable Long roomId, Model model) {
		ChatRoomAndMessageResponse chatRoomResponse = chatService.findRoomById(roomId);
		model.addAttribute("chatRoomDetailResponse", chatRoomResponse.getChatRoomDetailResponse());
		model.addAttribute("chatMessage", chatRoomResponse.getChatMessageDetailResponse());
		return "chat/roomDetails";
	}
}
