package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.enums.EntranceStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserPlanResponse {

	private final long planId;
	private final String openDate; // 오픈 일정
	private final String entranceStatus;// 진행단계
	private final String category; // 카테고리

	// 오픈 지점
	private final String office;
	private final String floor;
	private final String location;

	private final String contactInformation; // 연락처
	private final String createdDate; // 작성일
	private final String businessPlanUrl; // 사업 계획서

	public UserPlanResponse(Plan plan){
		this.planId = plan.getId();
		this.openDate = DateConvertor.convertDateFormatForResponse(plan.getOpenDate());
		this.entranceStatus = plan.getEntranceStatus().getName();
		this.category = plan.getCategory().getName();
		this.office = plan.getDepartmentBranch().getName();
		this.floor = plan.getFloor().getName();
		this.location = plan.getLocation();
		this.contactInformation = plan.getContactInformation();
		this.createdDate = DateConvertor.convertDateFormatForResponse(plan.getCreatedDate());
		this.businessPlanUrl = plan.getBusinessPlanUrl();
	}

	// // 팝업 스토어 게시글 정보
	// private final Long popupId;
	// private final String title; // 제목
	// private final String description; // 내용
	//
	// // 팝업스토어 부가 정보
	// private final String popupCratedDate; // 작성일
	// private final String modifiedState; // 수정 상태
	// private final String withdrawState; // 철회 상태
	// private final String amount; // 광고 금액
	// private final long popupView; // 조회수
}
