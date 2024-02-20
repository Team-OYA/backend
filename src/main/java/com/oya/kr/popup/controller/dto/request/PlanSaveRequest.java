package com.oya.kr.popup.controller.dto.request;

import java.time.LocalDate;

import com.oya.kr.global.domain.DateConvertor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanSaveRequest {

    private String office;
    private String floor;
    private String openDate;
    private String closeDate;
    private String contactInformation;
    private String category;

    public LocalDate getOpenDate() {
        return DateConvertor.convertDateFormatForRequest(openDate);
    }

    public LocalDate getCloseDate() {
        return DateConvertor.convertDateFormatForRequest(closeDate);
    }
}
