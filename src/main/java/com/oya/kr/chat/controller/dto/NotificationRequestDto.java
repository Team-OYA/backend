package com.oya.kr.chat.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationRequestDto {

	private final String title;
	private final String message;
	private final String token;
}