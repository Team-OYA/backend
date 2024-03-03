package com.oya.kr.payment.support.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentRequest {

    private final String paymentKey;
    private final String orderId;
    private final Long amount;
}
