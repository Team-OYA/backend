package com.oya.kr.payment.support;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
public interface PaymentProvider {

    String clientKey();

    String secretKey();

    String url();
}
