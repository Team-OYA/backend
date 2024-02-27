package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanAboutMeMapperResponse {

    private final long id;
    private final String office;
    private final String floor;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final String entranceStatus;
    private final String category;
    private final LocalDateTime createdDate;
    private final boolean writtenPopup;
}
