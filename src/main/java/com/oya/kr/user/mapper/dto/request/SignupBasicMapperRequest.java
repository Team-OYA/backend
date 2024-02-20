package com.oya.kr.user.mapper.dto.request;

import static com.oya.kr.global.domain.DateConvertor.*;
import static com.oya.kr.user.domain.User.*;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.response.KakaoInfo;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupBasicMapperRequest {
	private String nickname;
	private String email;
	private String password;
	private LocalDate birthDate;
	private String gender;
	private String registrationType;
	private String userType;
	private String profileUrl;

	public SignupBasicMapperRequest(BCryptPasswordEncoder bCryptPasswordEncoder, JoinRequest joinRequest){
		this.birthDate = convertDateFormatForRequest(joinRequest.getBirthDate());
		this.nickname = joinRequest.getNickname();
		this.email = joinRequest.getEmail();
		this.registrationType = RegistrationType.BASIC.getName();
		this.profileUrl = joinRequest.getProfileUrl();
		this.gender = Gender.getGenderEnum(joinRequest.getGender()).getName();
		this.password = encodePassword(bCryptPasswordEncoder, joinRequest.getPassword());
		this.userType = UserType.getUserTypeEnum(joinRequest.getUserType()).getName();
	}

	public SignupBasicMapperRequest(KakaoInfo kakaoInfo){
		this.birthDate = null;
		this.nickname = kakaoInfo.getNickname();
		this.email = kakaoInfo.getEmail();
		this.registrationType = RegistrationType.KAKAO.getName();
		this.profileUrl = kakaoInfo.getProfileImage();
		this.gender = null;
		this.password = null;
		this.userType = UserType.USER.getName();
	}

}
