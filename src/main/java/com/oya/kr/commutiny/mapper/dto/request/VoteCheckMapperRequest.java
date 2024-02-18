package com.oya.kr.commutiny.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoteCheckMapperRequest {
	private final long userId;
	private final long voteId;
}
