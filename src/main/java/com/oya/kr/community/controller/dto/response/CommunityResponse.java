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

	private final List<CommunityDetailResponse> communityDetailResponseList;
}
