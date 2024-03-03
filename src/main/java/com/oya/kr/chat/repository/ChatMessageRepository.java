package com.oya.kr.chat.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.chat.controller.dto.response.ChatMessageDetailResponse;
import com.oya.kr.chat.mapper.ChatMessageMapper;
import com.oya.kr.chat.mapper.dto.request.CreateMessageMapperRequest;
import com.oya.kr.chat.mapper.dto.response.MessageDetailMapperResponse;
import com.oya.kr.user.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.28
 */
@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

	private final ChatMessageMapper chatMessageMapper;

	/**
	 * 채팅 메시지 저장
	 *
	 * @author 이상민
	 * @since 2024.02.28
	 */
	public void save(CreateMessageMapperRequest createMessageMapperRequest) {
		chatMessageMapper.save(createMessageMapperRequest);
	}

	/**
	 * 이전 채팅 글 불러오기
	 *
	 * @author 이상민
	 * @since 2024.02.28
	 */
	public List<ChatMessageDetailResponse> findByChatRoomId(Long roomId, User user) {
		List<ChatMessageDetailResponse> responses = new ArrayList<>();
		List<MessageDetailMapperResponse> list = chatMessageMapper.findByChatRoomId(roomId);
		list.forEach(message -> {
			responses.add(new ChatMessageDetailResponse(message, user));
		});
		return responses;
	}
}
