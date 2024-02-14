package com.oya.kr.popup.domain;

import lombok.Getter;

@Getter
public enum Category {

	FOOD("food", "식품"),
	LIVING("living", "생활"),
	STATIONERY("stationery", "문구"),
	COSMETICS("cosmetics", "화장품"),
	CLOTHING("clothing", "의류"),
	K_POP("k-pop", "k-pop"),
	;

	private final String name;
	private final String description;

	Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
