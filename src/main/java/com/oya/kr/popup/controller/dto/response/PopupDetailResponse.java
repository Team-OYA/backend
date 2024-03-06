package com.oya.kr.popup.controller.dto.response;

import java.util.List;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.mapper.dto.response.MyPopupDetailMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupDetailResponse {

	private final boolean popupWritten; // 팝업 작성 상태

	private final long popupId;
	private final String title;
	private final String description;
	private final List<String> popupImages;

	// 팝업 스토어 게시글 부가 정보
	private final String createdDate; // 작성일
	private final String modifiedDate; // 수정 상태
	private final String withdrawalStatus; // 철회 상태
	private final Integer account; // 광고 금액
	private final int popupView; // 조회수

	public PopupDetailResponse(boolean popupWritten, MyPopupDetailMapper myPopupDetailMapper, List<String> images) {
		this.popupWritten = popupWritten;
		this.popupId = myPopupDetailMapper.getId();
		this.title = myPopupDetailMapper.getTitle();
		this.description = myPopupDetailMapper.getContent();
		this.popupImages = images;
		this.createdDate = DateConvertor.convertDateFormatForResponse(myPopupDetailMapper.getCreatedDate());
		this.modifiedDate = DateConvertor.convertDateFormatForResponse(myPopupDetailMapper.getModifiedDate());
		this.withdrawalStatus = myPopupDetailMapper.getWithdrawalStatus();
		this.account = myPopupDetailMapper.getAmount();
		this.popupView = myPopupDetailMapper.getPopupView();
	}

	public PopupDetailResponse(boolean popupWritten) {
		this.popupWritten = popupWritten;
		this.popupId = 0L;
		this.title = null;
		this.description = null;
		this.popupImages = null;
		this.createdDate = null;
		this.modifiedDate = null;
		this.withdrawalStatus = null;
		this.account = 0;
		this.popupView = 0;
	}
}
