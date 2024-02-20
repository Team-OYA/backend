package com.oya.kr.popup.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupSearchMapperRequest {

    private final String withdrawalStatus;
    private final int pageNo;
    private final int amount;
}
