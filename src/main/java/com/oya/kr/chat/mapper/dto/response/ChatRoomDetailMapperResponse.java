package com.oya.kr.chat.mapper.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatRoomDetailMapperResponse {

	private final Long id;
	private final String name;
	private final Long creator;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
}
