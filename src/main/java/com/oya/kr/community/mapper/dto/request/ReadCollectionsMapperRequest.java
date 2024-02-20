package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadCollectionsMapperRequest {

	private final long userId;
	private final boolean collectionDeleted;
	private final boolean communityDeleted;
	private final String userType;
	private final int pageNo;
	private final int amount;
}
