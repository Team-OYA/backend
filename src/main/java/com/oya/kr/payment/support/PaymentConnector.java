package com.oya.kr.payment.support;

import com.oya.kr.payment.support.dto.request.PaymentRequest;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
public interface PaymentConnector {

    void confirm(PaymentRequest request);
}
