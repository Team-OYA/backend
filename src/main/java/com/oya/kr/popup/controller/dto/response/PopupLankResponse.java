package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.mapper.dto.response.PopupTopMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupLankResponse {

	private int lank; // 순위

	private long planId;
	private String popupName; // My 팝업스토어
	private int popupView;

	public PopupLankResponse(int lank, PopupTopMapperResponse response) {
		this.lank = lank;
		this.planId= response.getPlanId();
		this.popupName = response.getTitle();
		this.popupView = response.getPopupView();
	}

	public void change(int lank, PopupTopMapperResponse response) {
		this.lank = lank;
		this.planId= response.getPlanId();
		this.popupName = response.getTitle();
		this.popupView = response.getPopupView();
	}
}
