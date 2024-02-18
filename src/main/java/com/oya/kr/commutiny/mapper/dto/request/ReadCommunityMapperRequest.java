package com.oya.kr.commutiny.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadCommunityMapperRequest {
	private final boolean deleted;
	private final String userType;
}
