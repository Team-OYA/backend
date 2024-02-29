package com.oya.kr.popup.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupTopMapperResponse {

	private final long userId;
	private final long planId;
	private final String title;
	private final int popupView;
}
