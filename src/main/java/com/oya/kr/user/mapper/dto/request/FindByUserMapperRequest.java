package com.oya.kr.user.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindByUserMapperRequest {

	private final Long userId;
	private final int pageNo;
	private final int amount;
}
