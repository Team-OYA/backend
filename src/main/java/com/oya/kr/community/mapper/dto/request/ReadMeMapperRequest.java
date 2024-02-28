package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadMeMapperRequest {

	private final Long userId;
	private final int pageNo;
	private final int amount;
}
