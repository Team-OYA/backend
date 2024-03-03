package com.oya.kr.popup.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupAdUpdateMapperRequest {

    private final String orderId;
    private final String paymentKey;
}
