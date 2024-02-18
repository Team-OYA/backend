package com.oya.kr.commutiny.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.oya.kr.commutiny.domain.Community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityResponse {
	private final long writeId;
	private final String nickname;
	private final String email;

	private final long communityId;
	private final String title;
	private final String description;
	private final String communityType;
	private final String category;
	private final String categoryDescription;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;

	private final List<VoteResponse> voteResponseList;

	public static CommunityResponse from(Community community, List<VoteResponse> voteResponseList) {
		return new CommunityResponse(
			community.getUser().getId(),
			community.getUser().getNickname(),
			community.getUser().getEmail(),
			community.getId(),
			community.getTitle(),
			community.getDescription(),
			community.getCommunityType().getName(),
			community.getCategory().getName(),
			community.getCategory().getDescription(),
			community.getCreatedDate(),
			community.getModifiedDate(),
			voteResponseList
		);
	}
}
