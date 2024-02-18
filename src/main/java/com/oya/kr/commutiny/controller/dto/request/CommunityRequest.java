package com.oya.kr.commutiny.controller.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
