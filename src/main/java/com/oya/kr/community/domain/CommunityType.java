package com.oya.kr.community.domain;

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
}
