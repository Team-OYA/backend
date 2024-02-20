package com.oya.kr.community.controller.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CommunityRequest {
	private final String title;
	private final String description;
	private final String categoryCode;

	// vote
	private final List<String> votes;
}
