package com.oya.kr.community.mapper.dto.response;

import java.time.LocalDateTime;

import com.oya.kr.community.domain.Community;
import com.oya.kr.community.domain.CommunityType;
import com.oya.kr.popup.domain.enums.Category;
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
	private final String categoryCode;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
	private final boolean deleted;
	private final int countView;

	public Community toDomain(User user) {
		return new Community(
			user,
			this.title,
			this.description,
			Category.from(this.categoryCode),
			CommunityType.from(this.communityType),
			this.id,
			this.createdDate,
			this.modifiedDate,
			this.deleted
		);
	}
}
