package com.oya.kr.chat.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.chat.controller.dto.request.ChatRoomCreateRequest;
import com.oya.kr.chat.controller.dto.response.ChatListResponse;
import com.oya.kr.chat.controller.dto.response.ChatRoomAndMessageResponse;
import com.oya.kr.chat.service.ChatService;
import com.oya.kr.global.dto.request.PaginationRequest;
import com.oya.kr.global.dto.response.ApplicationResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.03.02
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatRoomRestController {

	private final ChatService chatService;

	/**
	 * 나의 채팅방 리스트 조회
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	@GetMapping("/rooms")
	public ResponseEntity<ApplicationResponse<ChatListResponse>> showChatRooms(Principal principal,
		@RequestParam("pageNo") int pageNo, @RequestParam("amount") int amount) {
		ChatListResponse response =  chatService.findAllRoom(principal.getName(), new PaginationRequest(pageNo, amount));
		return ResponseEntity.ok(ApplicationResponse.success(response));
	}

	/**
	 * 관리자 채팅방 리스트 보여주기
	 *
	 * @author 이상민
	 * @since 2024.02.28
	 */
	@GetMapping("/rooms/admin")
	public ResponseEntity<ApplicationResponse<ChatListResponse>> adminShowChatRooms(Principal principal,
		@RequestParam("pageNo") int pageNo, @RequestParam("amount") int amount) {
		ChatListResponse response =  chatService.findAllRoom(new PaginationRequest(pageNo, amount));
		return ResponseEntity.ok(ApplicationResponse.success(response));
	}

	/**
	 * 채팅방 생성하기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	@PostMapping("/room")
	public ResponseEntity<ApplicationResponse<?>> createChatRoom(Principal principal, @RequestBody ChatRoomCreateRequest chatRoomCreateRequest) {
		chatService.createChatRoom(chatRoomCreateRequest.getRoomName(), principal.getName());
		return ResponseEntity.ok(ApplicationResponse.success("채팅방이 생성되었습니다."));
	}

	/**
	 * 메시지 불러오기
	 *
	 * @author 이상민
	 * @since 2024.03.03
	 */
	@GetMapping("/rooms/{roomId}")
	public ResponseEntity<ApplicationResponse<ChatRoomAndMessageResponse>> getMessages(Principal principal, @PathVariable Long roomId) {
		ChatRoomAndMessageResponse response =  chatService.findRoomById(roomId, principal.getName());
		return ResponseEntity.ok(ApplicationResponse.success(response));
	}
}
