package com.oya.kr.popup.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupAdSaveMapperRequest {

    private long id;
    private final Long popupId;
    private final String orderId;
    private final Long amount;
    private final String mainImageUrl;
}
