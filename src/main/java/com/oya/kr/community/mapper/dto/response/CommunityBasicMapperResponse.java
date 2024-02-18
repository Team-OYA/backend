package com.oya.kr.community.mapper.dto.response;

import java.time.LocalDateTime;

import com.oya.kr.community.domain.Community;
import com.oya.kr.community.domain.CommunityType;
import com.oya.kr.popup.domain.Category;
import com.oya.kr.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityBasicMapperResponse {

	private final long id;
	private final String title;
	private final String description;
	private final String communityType;
	private final long writeId;
	private final int popupId;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
	private final boolean deleted;

	public Community toDomain(User user) {
		Category categoryEnum = Category.findByIndex(this.popupId - 1);
		CommunityType communityTypeEnum = CommunityType.findByName(this.communityType);
		return new Community(
			user,
			this.title,
			this.description,
			categoryEnum,
			communityTypeEnum,
			this.id,
			this.createdDate,
			this.modifiedDate,
			this.deleted
		);
	}
}
