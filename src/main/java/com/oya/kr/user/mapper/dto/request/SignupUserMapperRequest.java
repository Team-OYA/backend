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

	private String nameOfCompany; // 상호
	private String nameOfRepresentative; // 대표자
	private LocalDate dateOfBusinessCommencement; // 개업일
	private String businessItem; // 종목
	private String connectedNumber;
	private String faxNumber;
	private String zipCode;
	private String businessAddress; // 사업장 소재지

	public SignupUserMapperRequest(BCryptPasswordEncoder bCryptPasswordEncoder, JoinRequest joinRequest) {
		this.birthDate = dateFormat(joinRequest.getBirthDate());
		this.nickname = joinRequest.getNickname();
		this.email = joinRequest.getEmail();
		this.businessRegistrationNumber = joinRequest.getBusinessRegistrationNumber();
		this.registrationType = RegistrationType.BASIC.getName();
		this.profileUrl = joinRequest.getProfileUrl();
		this.gender = Gender.getGenderEnum(joinRequest.getGender()).getName();
		this.password = encodePassword(bCryptPasswordEncoder, joinRequest.getPassword());
		this.userType = UserType.getUserTypeEnum(joinRequest.getUserType()).getName();
		this.nameOfCompany = joinRequest.getNameOfCompany();
		this.nameOfRepresentative = joinRequest.getNameOfRepresentative();
		this.dateOfBusinessCommencement = dateFormat(joinRequest.getDateOfBusinessCommencement());
		this.businessItem = joinRequest.getBusinessItem();
		this.connectedNumber = joinRequest.getConnectedNumber();
		this.faxNumber = joinRequest.getFaxNumber();
		this.zipCode = joinRequest.getZipCode();
		this.businessAddress = joinRequest.getBusinessAddress();
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
