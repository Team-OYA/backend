package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.19
 */
@Getter
@RequiredArgsConstructor
public class ReadCommunityMapperRequest {

	private final boolean deleted;
	private final String userType;
	private final int pageNo;
	private final int amount;
}
