package com.oya.kr.user.controller.dto.response;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.user.mapper.dto.response.BasicMapperResponse;
import com.oya.kr.user.mapper.dto.response.BusinessMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicUserResponse {

	private final Long userId;
	private final String nickname;
	private final String email;
	private final String birthDate;
	private final String gender;
	private final String registrationType;
	private final String userType;
	private final String profileUrl;
	private final String usersCreatedDate;
	private final String usersModifiedDate;
	private final boolean deleted;
	private final int communityCount;

	public BasicUserResponse(BasicMapperResponse basicMapperResponse){
		this.userId = basicMapperResponse.getUserId();
		this.nickname = basicMapperResponse.getNickname();
		this.email = basicMapperResponse.getEmail();
		this.birthDate = DateConvertor.convertDateFormatForResponse(basicMapperResponse.getBirthDate());
		this.gender = basicMapperResponse.getGender();
		this.registrationType = basicMapperResponse.getRegistrationType();
		this.userType = basicMapperResponse.getUserType();
		this.profileUrl = basicMapperResponse.getProfileUrl();
		this.usersCreatedDate = DateConvertor.convertDateFormatForResponse(basicMapperResponse.getUserCreatedDate());
		this.usersModifiedDate = DateConvertor.convertDateFormatForResponse(basicMapperResponse.getUserModifiedDate());
		this.deleted = basicMapperResponse.isUserDeleted();
		this.communityCount = basicMapperResponse.getCommunityCount();
	}

	public BasicUserResponse(BusinessMapperResponse businessMapperResponse) {
		this.userId = businessMapperResponse.getUserId();
		this.nickname = businessMapperResponse.getNickname();
		this.email = businessMapperResponse.getEmail();
		this.birthDate = DateConvertor.convertDateFormatForResponse(businessMapperResponse.getBirthDate());
		this.gender = businessMapperResponse.getGender();
		this.registrationType = businessMapperResponse.getRegistrationType();
		this.userType = businessMapperResponse.getUserType();
		this.profileUrl = businessMapperResponse.getProfileUrl();
		this.usersCreatedDate = DateConvertor.convertDateFormatForResponse(businessMapperResponse.getUserCreatedDate());
		this.usersModifiedDate = DateConvertor.convertDateFormatForResponse(businessMapperResponse.getUserModifiedDate());
		this.deleted = businessMapperResponse.isUserDeleted();
		this.communityCount = businessMapperResponse.getCommunityCount();
	}
}
