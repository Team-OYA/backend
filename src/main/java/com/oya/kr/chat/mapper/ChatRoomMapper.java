package com.oya.kr.chat.mapper;

import java.util.List;

import com.oya.kr.chat.mapper.dto.request.ChatRoomMapperRequest;
import com.oya.kr.chat.mapper.dto.request.CreateChatRoomMapperRequest;
import com.oya.kr.chat.mapper.dto.response.ChatRoomDetailMapperResponse;
import com.oya.kr.global.dto.request.PaginationRequest;

public interface ChatRoomMapper {
	void save(CreateChatRoomMapperRequest createChatRoomMapperRequest);
	List<ChatRoomDetailMapperResponse> findByAll(PaginationRequest paginationRequest);
	List<ChatRoomDetailMapperResponse> findRoomByUser(ChatRoomMapperRequest chatRoomMapperRequest);
	ChatRoomDetailMapperResponse findRoomById(Long roomId);

	int count(Long userId);

	int countAll();
}
