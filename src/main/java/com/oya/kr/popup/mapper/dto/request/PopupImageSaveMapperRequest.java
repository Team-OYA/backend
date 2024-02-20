package com.oya.kr.popup.mapper.dto.request;

import com.oya.kr.popup.domain.PopupImage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupImageSaveMapperRequest {

    private long popupImageId;
    private final long popupId;
    private final String url;

    public static PopupImageSaveMapperRequest from(PopupImage popupImage) {
        return new PopupImageSaveMapperRequest(popupImage.getPopup().getId(), popupImage.getUrl());
    }
}
