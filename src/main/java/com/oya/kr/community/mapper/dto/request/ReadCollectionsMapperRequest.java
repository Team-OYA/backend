package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.20
 */
@Getter
@RequiredArgsConstructor
public class ReadCollectionsMapperRequest {

	private final Long userId;
	private final boolean collectionDeleted;
	private final boolean communityDeleted;
	private final String userType;
	private final int pageNo;
	private final int amount;
}
