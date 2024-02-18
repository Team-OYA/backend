package com.oya.kr.community.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.oya.kr.community.domain.Community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityResponse {
	private final long writeId;
	private final String nickname;
	private final String email;
	private final String userType;

	private final long communityId;
	private final String title;
	private final String description;
	private final String communityType;
	private final String category;
	private final String categoryDescription;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;

	private final int countView;
	private final List<VoteResponse> voteResponseList;

	public static CommunityResponse from(Community community, int countView, List<VoteResponse> voteResponseList) {
		return new CommunityResponse(
			community.getUser().getId(),
			community.getUser().getNickname(),
			community.getUser().getEmail(),
			community.getUser().getUserType().name(),
			community.getId(),
			community.getTitle(),
			community.getDescription(),
			community.getCommunityType().getName(),
			community.getCategory().getName(),
			community.getCategory().getDescription(),
			community.getCreatedDate(),
			community.getModifiedDate(),
			countView,
			voteResponseList
		);
	}
}
