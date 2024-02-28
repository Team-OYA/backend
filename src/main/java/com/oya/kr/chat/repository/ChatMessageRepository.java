package com.oya.kr.chat.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.chat.controller.dto.ChatMessageRequest;
import com.oya.kr.chat.controller.dto.response.ChatMessageDetailResponse;
import com.oya.kr.chat.mapper.ChatMessageMapper;
import com.oya.kr.chat.mapper.dto.request.CreateMessageMapperRequest;
import com.oya.kr.chat.mapper.dto.response.ChatRoomDetailMapperResponse;
import com.oya.kr.chat.mapper.dto.response.MessageDetailMapperResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

	private final ChatMessageMapper chatMessageMapper;

	public void save(CreateMessageMapperRequest createMessageMapperRequest) {
		chatMessageMapper.save(createMessageMapperRequest);
	}

	public List<ChatMessageDetailResponse> findByChatRoomId(Long roomId) {
		List<ChatMessageDetailResponse> responses = new ArrayList<>();
		List<MessageDetailMapperResponse> list = chatMessageMapper.findByChatRoomId(roomId);
		list.forEach(message -> {
			responses.add(new ChatMessageDetailResponse(message));
		});
		return responses;
	}
}
