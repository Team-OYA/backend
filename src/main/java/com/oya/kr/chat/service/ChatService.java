package com.oya.kr.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.chat.controller.dto.ChatMessageRequest;
import com.oya.kr.chat.controller.dto.response.ChatMessageDetailResponse;
import com.oya.kr.chat.controller.dto.response.ChatRoomAndMessageResponse;
import com.oya.kr.chat.controller.dto.response.ChatRoomDetailResponse;
import com.oya.kr.chat.mapper.dto.request.CreateChatRoomMapperRequest;
import com.oya.kr.chat.mapper.dto.request.CreateMessageMapperRequest;
import com.oya.kr.chat.repository.ChatMessageRepository;
import com.oya.kr.chat.repository.ChatRoomRepository;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserRepository userRepository;

	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;

	/**
	 * 채팅방 생성하기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	public void createChatRoom(String roomName){
		chatRoomRepository.createChatRoom(new CreateChatRoomMapperRequest(roomName));
	}

	/**
	 * 채팅방 리스트 보여주기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	public List<ChatRoomDetailResponse> findAllRoom(Long userId){
		return chatRoomRepository.findAllRoom(userId);
	}

	/**
	 * 채팅방 정보 + 채팅 리스트 보여주기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	public ChatRoomAndMessageResponse findRoomById(Long roomId){
		ChatRoomDetailResponse chatRoomDetailResponse = chatRoomRepository.findRoomById(roomId);
		List<ChatMessageDetailResponse> chatMessageDetailResponse = chatMessageRepository.findByChatRoomId(roomId);
		return new ChatRoomAndMessageResponse(chatRoomDetailResponse, chatMessageDetailResponse);
	}

	/**
	 * 메시지 저장하기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	public void saveMessage(ChatMessageRequest message){
		chatMessageRepository.save(new CreateMessageMapperRequest(message));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<ChatMessageRequest> findByChatRoomId(Long roomId) {
		List<ChatMessageDetailResponse> responses = chatMessageRepository.findByChatRoomId(roomId);
		List<ChatMessageRequest> list = new ArrayList<>();
		responses.forEach(detail ->{
			list.add(new ChatMessageRequest(detail));
		});
		return list;
	}
}
