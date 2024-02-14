package com.oya.kr.user.mapper.dto.request;

import static com.oya.kr.user.domain.User.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupUserMapperRequest {
	private String nickname;
	private String email;
	private String password;
	private LocalDate birthDate;
	private String gender;
	private String registrationType;
	private String userType;
	private String businessRegistrationNumber;
	private String profileUrl;

	public SignupUserMapperRequest(BCryptPasswordEncoder bCryptPasswordEncoder, JoinRequest joinRequest) {
		this.birthDate = dateFormat(joinRequest.getBirthDate());
		this.nickname = joinRequest.getNickname();
		this.email = joinRequest.getEmail();
		this.businessRegistrationNumber = joinRequest.getBusinessRegistrationNumber();
		this.registrationType = RegistrationType.BASIC.name();
		this.profileUrl = joinRequest.getProfileUrl();
		this.gender = Gender.getGenderEnum(joinRequest.getGender()).name();
		this.password = encodePassword(bCryptPasswordEncoder, joinRequest.getPassword());
		this.userType = UserType.getUserTypeEnum(joinRequest.getUserType()).name();
	}

	public LocalDate dateFormat(String date) {
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			return LocalDate.parse(date, formatter);
		} else {
			return null;
		}
	}
}
