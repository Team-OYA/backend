package com.oya.kr.user.service.dto;

import static com.oya.kr.user.domain.User.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinRequestDto {

	private final String nickname;
	private final String email;
	private String password;
	private LocalDate birthDate;
	private Gender gender;
	private final RegistrationType registrationType;
	private UserType userType;
	private final String businessRegistrationNumber;
	private final String profileUrl;

	// 사업체
	private String nameOfCompany; // 상호
	private String nameOfRepresentative; // 대표자
	private LocalDate dateOfBusinessCommencement; // 개업일
	private String businessItem; // 종목
	private String connectedNumber;
	private String faxNumber;
	private String businessAddress; // 사업장 소재지

	public JoinRequestDto(BCryptPasswordEncoder bCryptPasswordEncoder, JoinRequest joinRequest) {
		this.birthDate = dateFormat(joinRequest.getBirthDate());
		this.nickname = joinRequest.getNickname();
		this.email = joinRequest.getEmail();
		this.businessRegistrationNumber = joinRequest.getBusinessRegistrationNumber();
		this.registrationType = RegistrationType.BASIC;
		this.profileUrl = joinRequest.getProfileUrl();
		this.gender = Gender.getGenderEnum(joinRequest.getGender());
		this.password = encodePassword(bCryptPasswordEncoder, joinRequest.getPassword());
		this.userType = UserType.getUserTypeEnum(joinRequest.getUserType());

		if(joinRequest.getUserType() == 1){
			this.nameOfCompany = joinRequest.getNameOfCompany();
			this.nameOfRepresentative = joinRequest.getNameOfRepresentative();
			this.businessItem = joinRequest.getBusinessItem();
			this.connectedNumber = joinRequest.getConnectedNumber();
			this.dateOfBusinessCommencement = dateFormat(joinRequest.getDateOfBusinessCommencement());
			this.faxNumber = joinRequest.getFaxNumber();
			this.businessAddress = joinRequest.getBusinessAddress();
		}
	}

	public LocalDate dateFormat(String date){
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
			return LocalDate.parse(date, formatter);
		} else {
			return null;
		}
	}

}
