package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.DepartmentBranch;
import com.oya.kr.popup.domain.enums.DepartmentFloor;
import com.oya.kr.popup.domain.enums.EntranceStatus;
import com.oya.kr.popup.mapper.dto.response.MyPlanMapperResponse;

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

	public UserPlanResponse(MyPlanMapperResponse myPlanMapperResponse){
		this.planId = myPlanMapperResponse.getId();
		this.openDate = DateConvertor.convertDateFormatForResponse(myPlanMapperResponse.getOpenDate());
		this.entranceStatus = EntranceStatus.from(myPlanMapperResponse.getEntranceStatus()).getDescription();
		this.category = Category.from(myPlanMapperResponse.getCategory()).getDescription();
		this.office = DepartmentBranch.from(myPlanMapperResponse.getOffice()).getDescription();
		this.floor = DepartmentFloor.from(myPlanMapperResponse.getFloor()).getDescription();
		this.location = myPlanMapperResponse.getLocation();
		this.contactInformation = myPlanMapperResponse.getContactInformation();
		this.createdDate = DateConvertor.convertDateFormatForResponse(myPlanMapperResponse.getCreatedDate());
		this.businessPlanUrl = myPlanMapperResponse.getBusinessPlanUrl();
	}
}
