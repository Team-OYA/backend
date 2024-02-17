package com.oya.kr.user.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Business extends Base {

	private Long id;
	private User user;
	private String nameOfCompany; // 상호
	private String nameOfRepresentative; // 대표자
	private LocalDate dateOfBusinessCommencement; // 개업일
	private String businessItem; // 종목
	private String connectedNumber;
	private String faxNumber;
	private String zipCode;
	private String businessAddress; // 사업장 소재지
}
