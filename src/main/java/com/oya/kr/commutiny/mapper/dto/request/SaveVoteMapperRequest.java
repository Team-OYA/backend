package com.oya.kr.commutiny.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveVoteMapperRequest {

	private final String content;
	private final long postId;
}
