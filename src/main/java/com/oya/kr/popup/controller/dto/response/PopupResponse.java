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
public class PopupResponse {

    private final long popupId;
    private final long planId;
    private final String title;
    private final String description;
    private final LocalDateTime pulledDate;
    private final CategoryResponse category;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final boolean written;
    private final boolean collected;

    public static PopupResponse from(PopupDetailMapperResponse response, Long userId) {
        return new PopupResponse(
            response.getId(),
            response.getPlanId(),
            response.getTitle(),
            response.getDescription(),
            response.getPulledDate(),
            CategoryResponse.from(Category.from(response.getCategory())),
            response.getOpenDate(),
            response.getCloseDate(),
            userId.equals(response.getUserId()),
            response.isCollected()
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
