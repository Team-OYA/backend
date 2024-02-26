package com.oya.kr.community.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@Getter
@RequiredArgsConstructor
public class CommunityResponse {

	private final int listSize; // 전체 게시글 리스트
	private final List<CommunityDetailResponse> communityDetailResponseList;
}
