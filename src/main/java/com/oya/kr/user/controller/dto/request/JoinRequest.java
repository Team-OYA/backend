package com.oya.kr.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class JoinRequest {

	private String nickname;
	private final String email;
	private String password;

	private String birthDate;

	private int gender;
	private int userType;

	private String businessRegistrationNumber;
	private String profileUrl;

	// 사업체
	private String nameOfCompany; // 상호
	private String nameOfRepresentative; // 대표자
	private String dateOfBusinessCommencement; // 개업일
	private String businessItem; // 종목
	private String connectedNumber;
	private String faxNumber;
	private String businessAddress; // 사업장 소재지

	public JoinRequest(String nickname, String email, String password, String birthDate, int gender, int userType,
		String businessRegistrationNumber, String profileUrl) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.gender = gender;
		this.userType = userType;
		this.businessRegistrationNumber = businessRegistrationNumber;
		this.profileUrl = profileUrl;
	}

	public JoinRequest(String email) {
		this.email = email;
	}

	public JoinRequest(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}
}
