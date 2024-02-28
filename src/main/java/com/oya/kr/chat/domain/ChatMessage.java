package com.oya.kr.chat.domain;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage extends Base {

	private Long id;
	private String content;
	private ChatRoom chatRoom;
	private User sender;
}
