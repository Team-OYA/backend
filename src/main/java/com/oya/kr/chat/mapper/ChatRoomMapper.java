package com.oya.kr.chat.mapper;

import java.util.List;

import com.oya.kr.chat.mapper.dto.request.CreateChatRoomMapperRequest;
import com.oya.kr.chat.mapper.dto.response.ChatRoomDetailMapperResponse;

public interface ChatRoomMapper {
	void save(CreateChatRoomMapperRequest createChatRoomMapperRequest);
	List<ChatRoomDetailMapperResponse> findByAll();
	ChatRoomDetailMapperResponse findRoomById(Long roomId);
}
