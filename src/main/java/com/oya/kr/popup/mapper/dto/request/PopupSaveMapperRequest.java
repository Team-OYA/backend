package com.oya.kr.popup.mapper.dto.request;

import com.oya.kr.popup.domain.Popup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupSaveMapperRequest {

    private long popupId;
    private final long planId;
    private final String title;
    private final String description;
    private final String withdrawalStatus;

    public static PopupSaveMapperRequest from(Popup popup) {
        return new PopupSaveMapperRequest(
            popup.getPlan().getId(),
            popup.getTitle(),
            popup.getDescription(),
            popup.getWithdrawalStatus().getName()
        );
    }
}
