package com.oya.kr.chat.controller.dto.response;

import com.oya.kr.chat.mapper.dto.response.MessageDetailMapperResponse;
import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatMessageDetailResponse {

	private final String senderNickname;
	private final String message;
	private final String sendingTime;
	private final String userType;
	private final boolean isCheckedMe;

	public ChatMessageDetailResponse(MessageDetailMapperResponse message, User user) {
		this.senderNickname = message.getNICKNAME();
		this.message = message.getContent();
		this.sendingTime = DateConvertor.convertDateFormatForResponse(message.getModifiedDate());

		if(message.getUserType().equals("administrator")){
			this.userType = "admin";
		}else{
			this.userType = "user";
		}

		if(user.getNickname().equals(this.senderNickname)){
			this.isCheckedMe = true;
		}else{
			isCheckedMe = false;
		}
	}
}
