package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CollectionMapperRequest {

	private final long communityId;
	private final long userId;
	private int isDeleted;

	public void updateIsDeleted(boolean isDeleted){
		if(isDeleted){
			this.isDeleted = 0;
		}else{
			this.isDeleted = 1;
		}
	}
}
