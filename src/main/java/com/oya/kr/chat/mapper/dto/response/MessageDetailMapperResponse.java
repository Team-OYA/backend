package com.oya.kr.chat.mapper.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageDetailMapperResponse {

	private final Long sender;
	private final String NICKNAME;
	private final String content;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
}
