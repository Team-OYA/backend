package com.oya.kr.community.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.oya.kr.community.domain.enums.CommunityType;
import com.oya.kr.global.domain.Base;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Community extends Base {

	private Long id;
	private User user;
	private String title;
	private String description;
	private Category category;
	private CommunityType communityType;

	public Community(User user, String title, String description, Category category, CommunityType communityType,
		long id, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean deleted) {
		this.user = user;
		this.title = title;
		this.description = description;
		this.category = category;
		this.communityType = communityType;
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.deleted = deleted;
	}
}
