package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;


@Getter
public class MyPopupDetailMapper {
	private final long id;
	private final String title;
	private final String content;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
	private final String withdrawalStatus;
	private final Integer amount;
	private final int popupView;

	public MyPopupDetailMapper(long id, String title, String content,
		LocalDateTime createdDate, LocalDateTime modifiedDate,
		String withdrawalStatus, Integer amount, int popupView) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.withdrawalStatus = withdrawalStatus;
		this.amount = amount;
		this.popupView = popupView;
	}
}