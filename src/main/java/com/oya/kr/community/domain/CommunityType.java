package com.oya.kr.community.domain;

import static com.oya.kr.community.exception.CommunityErrorCodeList.*;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

@Getter
public enum CommunityType {

	BASIC("basic", "기본 게시글"),
	VOTE("vote", "투표 게시글"),
	;

	private final String name;
	private final String description;

	CommunityType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * name으로 CommunityType 찾기
	 *
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public static CommunityType from(String name) {
		return Arrays.stream(CommunityType.values())
			.filter(enumValue -> enumValue.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_COMMUNITY_TYPE));
	}
}
