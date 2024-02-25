package com.oya.kr.user.controller.dto.response;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.user.mapper.dto.response.BusinessMapperResponse;

import lombok.Getter;

@Getter
public class BusinessUserResponse extends BasicUserResponse{

	private final Long businessId;
	private final String businessRegistrationNumber;
	private final String nameOfCompany; // 상호
	private final String nameOfRepresentative; // 대표자
	private final String dateOfBusinessCommencement; // 개업일
	private final String businessItem; // 종목
	private final String connectedNumber;
	private final String faxNumber;
	private final String zipCode;
	private final String businessAddress; // 사업장 소재지
	private final int planCount;
	private final int popupCount;

	public BusinessUserResponse(BusinessMapperResponse businessMapperResponse){
		super(businessMapperResponse);
		this.businessId = businessMapperResponse.getBusinessId();
		this.nameOfCompany = businessMapperResponse.getNameOfCompany();
		this.nameOfRepresentative = businessMapperResponse.getNameOfRepresentative();
		this.dateOfBusinessCommencement = DateConvertor.convertDateFormatForResponse(businessMapperResponse.getDateOfBusinessCommencement());
		this.businessItem = businessMapperResponse.getBusinessItem();
		this.connectedNumber = businessMapperResponse.getConnectedNumber();
		this.faxNumber = businessMapperResponse.getFaxNumber();
		this.zipCode = businessMapperResponse.getZipCode();
		this.businessAddress = businessMapperResponse.getBusinessAddress();
		this.planCount = businessMapperResponse.getPlanCount();
		this.popupCount = businessMapperResponse.getPopupCount();
		this.businessRegistrationNumber = businessMapperResponse.getBusinessRegistrationNumber();
	}
}
