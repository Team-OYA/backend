package com.oya.kr.popup.controller.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.oya.kr.global.domain.DateConvertor;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupListResponse {

    private final long planId;
    private final long popupId;
    private final String title;
    private final String description;
    private final LocalDateTime pulledDate;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final String thumbnail;
    private final CategoryResponse category;

    public static PopupListResponse from(PopupDetailMapperResponse response) {
        return new PopupListResponse(
            response.getPlanId(),
            response.getId(),
            response.getTitle(),
            response.getDescription(),
            response.getPulledDate(),
            response.getOpenDate(),
            response.getCloseDate(),
            response.getThumbnail(),
            CategoryResponse.from(Category.from(response.getCategory()))
        );
    }

    public String getPulledDate() {
        return DateConvertor.convertDateFormatForResponse(pulledDate);
    }

    public String getOpenDate() {
        return DateConvertor.convertDateFormatForResponse(openDate);
    }

    public String getCloseDate() {
        return DateConvertor.convertDateFormatForResponse(closeDate);
    }
}
