package com.oya.kr.user.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BusinessMapperResponse extends BasicMapperResponse{

	private final Long businessId;
	private final String nameOfCompany; // 상호
	private final String nameOfRepresentative; // 대표자
	private final LocalDate dateOfBusinessCommencement; // 개업일
	private final String businessItem; // 종목
	private final String connectedNumber;
	private final String faxNumber;
	private final String zipCode;
	private final String businessAddress; // 사업장 소재지
	private final String businessRegistrationNumber;

	private final int planCount;
	private final int popupCount;

	public BusinessMapperResponse(Long userId, String nickname, String email, LocalDate birthDate, String gender,
		String registrationType, String userType, String profileUrl, LocalDateTime userCreatedDate,
		LocalDateTime userModifiedDate, boolean userDeleted, int communityCount, Long businessId, String nameOfCompany,
		String nameOfRepresentative, LocalDate dateOfBusinessCommencement, String businessItem, String connectedNumber,
		String faxNumber, String zipCode, String businessAddress, int planCount, int popupCount, String businessRegistrationNumber) {
		super(userId, nickname, email, birthDate, gender, registrationType, userType, profileUrl, userCreatedDate,
			userModifiedDate, userDeleted, communityCount);
		this.businessId = businessId;
		this.nameOfCompany = nameOfCompany;
		this.nameOfRepresentative = nameOfRepresentative;
		this.dateOfBusinessCommencement = dateOfBusinessCommencement;
		this.businessItem = businessItem;
		this.connectedNumber = connectedNumber;
		this.faxNumber = faxNumber;
		this.zipCode = zipCode;
		this.businessAddress = businessAddress;
		this.planCount = planCount;
		this.popupCount = popupCount;
		this.businessRegistrationNumber = businessRegistrationNumber;
	}
}
