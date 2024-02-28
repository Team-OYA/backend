package com.oya.kr.chat.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	private final SimpMessagingTemplate messagingTemplate;

	/**
	 * 채팅방 리스트 보여주기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	@GetMapping("/rooms")
	public String showChatRooms(Model model) {
		model.addAttribute("chatRoomResponses", chatService.findAllRoom(1L));
		return "chat/roomList";
	}

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

	/**
	 * 채팅방 생성하기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	@PostMapping("/createRoom")
	public String createChatRoom(@RequestParam String roomName) {
		chatService.createChatRoom(roomName);
		return "redirect:/chat/rooms";
	}
}
