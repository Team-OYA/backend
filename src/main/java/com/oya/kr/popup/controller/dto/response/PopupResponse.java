package com.oya.kr.popup.controller.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupResponse {

    private final long popupId;
    private final long planId;
    private final String title;
    private final String description;
    private final LocalDateTime pulledDate;
    private final CategoryResponse category;
    private final LocalDate openDate;
    private final LocalDate closeDate;

    public static PopupResponse from(PopupDetailMapperResponse response) {
        return new PopupResponse(
            response.getId(),
            response.getPlanId(),
            response.getTitle(),
            response.getDescription(),
            response.getPulledDate(),
            CategoryResponse.from(Category.from(response.getCategory())),
            response.getOpenDate(),
            response.getCloseDate()
        );
    }

    public String getPulledDate() {
        return String.format(
            "%d-%02d-%dT%02d:%02d:%02d",
            pulledDate.getYear(),
            pulledDate.getMonthValue(),
            pulledDate.getDayOfMonth(),
            pulledDate.getHour(),
            pulledDate.getMinute(),
            pulledDate.getSecond());
    }

    public String getOpenDate() {
        return String.format(
            "%d-%02d-%d",
            openDate.getYear(),
            openDate.getMonthValue(),
            openDate.getDayOfMonth()
        );
    }

    public String getCloseDate() {
        return String.format(
            "%d-%02d-%d",
            closeDate.getYear(),
            closeDate.getMonthValue(),
            closeDate.getDayOfMonth()
        );
    }
}
