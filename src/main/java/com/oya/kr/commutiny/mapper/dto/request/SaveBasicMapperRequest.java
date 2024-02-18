package com.oya.kr.commutiny.mapper.dto.request;

import com.oya.kr.commutiny.controller.dto.request.CommunityRequest;
import com.oya.kr.commutiny.domain.CommunityType;
import com.oya.kr.popup.domain.Category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveBasicMapperRequest {

	private long postId;
	private final String title;
	private final String description;
	private final String communityType;
	private final long writeId;
	private final long popupid;

	public SaveBasicMapperRequest(String CommunityType, long userId, CommunityRequest communityRequest) {
		this.title = communityRequest.getTitle();
		this.description = communityRequest.getDescription();
		this.communityType = CommunityType;
		this.writeId = userId;
		this.popupid = Category.findByCode(communityRequest.getCategoryCode()).ordinal() + 1;
	}
}
