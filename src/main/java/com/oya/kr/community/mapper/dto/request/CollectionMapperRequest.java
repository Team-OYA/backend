package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.19
 */
@Getter
@RequiredArgsConstructor
public class CollectionMapperRequest {

	private final long communityId;
	private final long userId;
	private int isDeleted;

	/**
	 * 스크랩 상태 변경
	 *
	 * @author 이상민
	 * @since 2024.02.20
	 */
	public void updateIsDeleted(boolean isDeleted){
		if(isDeleted){
			this.isDeleted = 0;
		}else{
			this.isDeleted = 1;
		}
	}
}
