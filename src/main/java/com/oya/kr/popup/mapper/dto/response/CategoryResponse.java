package com.oya.kr.popup.mapper.dto.response;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

	private final long id;
	private final String name;
}
