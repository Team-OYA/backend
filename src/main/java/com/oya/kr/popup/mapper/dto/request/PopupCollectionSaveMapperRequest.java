package com.oya.kr.popup.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupCollectionSaveMapperRequest {

    private long popupCollectionId;
    private final Long userId;
    private final Long popupId;
}
