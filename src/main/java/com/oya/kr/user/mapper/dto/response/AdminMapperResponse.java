package com.oya.kr.user.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminMapperResponse {

	private final Long userId;
	private final String nickname;
	private final String email;
	private final String password;
	private final LocalDate birthDate;
	private final String gender;
	private final String registrationType;
	private final String userType;
	private final String businessRegistrationNumber;
	private final String profileUrl;
	private final LocalDateTime usersCreatedDate;
	private final LocalDateTime usersModifiedDate;
	private final boolean deleted;

	private final Long businessId;
	private final String nameOfCompany; // 상호
	private final String nameOfRepresentative; // 대표자
	private final LocalDate dateOfBusinessCommencement; // 개업일
	private final String businessItem; // 종목
	private final String connectedNumber;
	private final String faxNumber;
	private final String zipCode;
	private final String businessAddress; // 사업장 소재지
}
