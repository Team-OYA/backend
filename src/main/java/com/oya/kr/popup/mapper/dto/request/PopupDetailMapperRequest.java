package com.oya.kr.popup.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupDetailMapperRequest {

    private final Long popupId;
    private final Long userId;
}
