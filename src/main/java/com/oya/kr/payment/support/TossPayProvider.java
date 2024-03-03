package com.oya.kr.payment.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
@Component
public class TossPayProvider implements PaymentProvider {

    @Value("${payment.toss.client-key}")
    private String clientKey;

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    @Value("${payment.toss.url}")
    private String url;

    @Override
    public String clientKey() {
        return clientKey;
    }

    @Override
    public String secretKey() {
        return secretKey;
    }

    @Override
    public String url() {
        return url;
    }
}
