package com.oya.kr.chat.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.chat.mapper.dto.request.CreateChatRoomMapperRequest;
import com.oya.kr.common.SpringApplicationTest;

class ChatRoomMapperTest extends SpringApplicationTest {

	@Autowired
	private ChatRoomMapper chatRoomMapper;

	@DisplayName("채팅방을 생성할 수 있다.")
	@Test
	void save(){
		// given
		CreateChatRoomMapperRequest createChatRoomMapperRequest = new CreateChatRoomMapperRequest("test");
		// when
		chatRoomMapper.save(createChatRoomMapperRequest);
		// then
	}
}