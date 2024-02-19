package com.oya.kr.community.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityResponse {

	private final List<CommunityDetailResponse> communityDetailResponseList;
}
