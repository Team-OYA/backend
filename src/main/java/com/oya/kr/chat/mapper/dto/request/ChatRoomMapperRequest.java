package com.oya.kr.chat.mapper.dto.request;

import com.oya.kr.global.dto.request.PaginationRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatRoomMapperRequest {

	private final Long userId;
	private final int pageNo;
	private final int amount;

	public ChatRoomMapperRequest(Long userId, PaginationRequest paginationRequest){
		this.userId = userId;
		this.pageNo = paginationRequest.getPageNo();
		this.amount = paginationRequest.getAmount();
	}
}
