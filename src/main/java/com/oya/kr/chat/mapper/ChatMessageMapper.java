package com.oya.kr.chat.mapper;

import java.util.List;

import com.oya.kr.chat.mapper.dto.request.CreateMessageMapperRequest;
import com.oya.kr.chat.mapper.dto.response.MessageDetailMapperResponse;

public interface ChatMessageMapper {
	void save(CreateMessageMapperRequest createMessageMapperRequest);

	List<MessageDetailMapperResponse> findByChatRoomId(Long roomId);
}
