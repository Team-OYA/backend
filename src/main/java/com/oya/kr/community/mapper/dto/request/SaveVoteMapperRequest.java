package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.19
 */
@Getter
@RequiredArgsConstructor
public class SaveVoteMapperRequest {

	private final String content;
	private final long postId;
}
