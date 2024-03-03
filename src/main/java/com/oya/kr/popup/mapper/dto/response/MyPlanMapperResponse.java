package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPlanMapperResponse {

	private final long id;
	private final String office;
	private final String floor;
	private final String location;
	private final LocalDate openDate;
	private final LocalDate closeDate;
	private final String businessPlanUrl;
	private final String entranceStatus;
	private final String contactInformation;
	private final String category;
	private final Long userId;
	private final LocalDateTime createdDate;
}
