package com.oya.kr.user.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class JoinRequest {

	private final String email;
	private final String nickname;
	private final String password;
	private final String birthDate;
	private final int gender;
	private final int userType;
	private final String businessRegistrationNumber;
	private final String profileUrl;

	// 사업체
	private final String nameOfCompany; // 상호
	private final String nameOfRepresentative; // 대표자
	private final String dateOfBusinessCommencement; // 개업일
	private final String businessItem; // 종목
	private final String connectedNumber;
	private final String faxNumber;
	private final String zipCode;
	private final String businessAddress; // 사업장 소재지
}
