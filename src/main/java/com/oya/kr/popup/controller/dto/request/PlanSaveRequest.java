package com.oya.kr.popup.controller.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlanSaveRequest {

    private final String office;
    private final String floor;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final String contactInformation;
    private final String category;
}
