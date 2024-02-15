package com.oya.kr.user.mapper.dto.request;

import com.oya.kr.user.controller.dto.response.KakaoInfo;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupKakaoRequest {

	private String nickname;
	private String email;
	private String profileUrl;

	private String registrationType;
	private String userType;

	public SignupKakaoRequest(KakaoInfo kakaoInfo) {
		this.nickname = kakaoInfo.getNickname();
		this.email = kakaoInfo.getEmail();
		this.profileUrl = kakaoInfo.getProfileImage();

		this.registrationType = RegistrationType.KAKAO.getName();
		this.userType = UserType.USER.getName();
	}
}
