package com.oya.kr.community.mapper.dto.request;

import com.oya.kr.community.controller.dto.request.CommunityRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.19
 */
@Getter
@RequiredArgsConstructor
public class SaveBasicMapperRequest {

	private long postId;
	private final String title;
	private final String description;
	private final String communityType;
	private final long writeId;
	private final String categoryCode;

	public SaveBasicMapperRequest(String CommunityType, long userId, CommunityRequest communityRequest) {
		this.title = communityRequest.getTitle();
		this.description = communityRequest.getDescription();
		this.communityType = CommunityType;
		this.writeId = userId;
		this.categoryCode = communityRequest.getCategoryCode();
	}
}
