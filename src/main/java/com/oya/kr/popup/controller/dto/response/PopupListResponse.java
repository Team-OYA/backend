package com.oya.kr.popup.controller.dto.response;

import java.time.LocalDateTime;

import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupListResponse {

    private final long planId;
    private final String title;
    private final String description;
    private final LocalDateTime pulledDate;
    private final LocalDateTime createdDate;
    private final String thumbnail;
    private final CategoryResponse category;

    public static PopupListResponse from(PopupDetailMapperResponse response) {
        return new PopupListResponse(
            response.getPlanId(),
            response.getTitle(),
            response.getDescription(),
            response.getPulledDate(),
            response.getCreatedDate(),
            response.getThumbnail(),
            CategoryResponse.from(Category.from(response.getCategory()))
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

    public String getCreatedDate() {
        return String.format(
            "%d-%02d-%dT%02d:%02d:%02d",
            createdDate.getYear(),
            createdDate.getMonthValue(),
            createdDate.getDayOfMonth(),
            createdDate.getHour(),
            createdDate.getMinute(),
            createdDate.getSecond());
    }
}
