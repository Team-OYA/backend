package com.oya.kr.community.controller.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.oya.kr.community.domain.Community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@Getter
@RequiredArgsConstructor
public class CommunityDetailResponse {

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
	private final String createdDate;
	private final String modifiedDate;

	private final int countView;
	private final List<VoteResponse> voteResponseList;
	private final List<String> imageList;

	private final boolean written;
	private final boolean collected;

	public static CommunityDetailResponse from(List<String> imageList, Community community, int countView,
		List<VoteResponse> voteResponseList, Long userId, boolean collected) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		String createdDateString = community.getCreatedDate().format(formatter);
		String modifiedDateString = community.getModifiedDate().format(formatter);

		return new CommunityDetailResponse(
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
			createdDateString,
			modifiedDateString,
			countView,
			voteResponseList,
			imageList,
			userId.equals(community.getUser().getId()),
			collected
		);
	}
}
