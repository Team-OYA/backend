package com.oya.kr.popup.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupAdMapperResponse {

    private final long id;
    private final long amount;
    private final long popupId;
    private final String orderId;
    private final String paymentKey;
}
