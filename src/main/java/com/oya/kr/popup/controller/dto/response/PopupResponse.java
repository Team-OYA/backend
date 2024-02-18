package com.oya.kr.popup.controller.dto.response;

import java.time.LocalDateTime;

import com.oya.kr.popup.domain.Popup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupResponse {

    private final long planId;
    private final String title;
    private final String description;
    private final LocalDateTime pulledDate;
    private final LocalDateTime createdDate;

    public static PopupResponse from(Popup popup) {
        return new PopupResponse(
            popup.getPlan().getId(),
            popup.getTitle(),
            popup.getDescription(),
            popup.getPulledDate(),
            popup.getCreatedDate()
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
