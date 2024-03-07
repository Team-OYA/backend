package com.oya.kr.payment.support;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.oya.kr.global.support.ApiConnector;
import com.oya.kr.payment.support.dto.request.PaymentRequest;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
@Component
@RequiredArgsConstructor
public class TossPayConnector implements PaymentConnector {

    private static final String TOKEN_TYPE = "Basic";

    private final PaymentProvider tossPayProvider;

    /**
     * 토스페이 결제 처리
     *
     * @parameter PaymentRequest
     * @return ResponseEntity<Void>
     * @author 김유빈
     * @since 2024.02.29
     */
    @Override
    public ResponseEntity<Void> success(PaymentRequest request) {
        HttpEntity<Map<String, Object>> entity = createRequest(request);
        return ApiConnector.post(tossPayProvider.url(), entity, Void.class);
    }

    private HttpEntity<Map<String, Object>> createRequest(PaymentRequest request) {
        HttpHeaders headers = createHeaders();
        Map<String, Object> body = createBody(request);
        return new HttpEntity<>(body, headers);
    }

    private Map<String, Object> createBody(PaymentRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", request.getPaymentKey());
        body.put("orderId", request.getOrderId());
        body.put("amount", request.getAmount());
        return body;
    }

    private HttpHeaders createHeaders() {
        byte[] encodedBytes = Base64.getEncoder().encode((tossPayProvider.secretKey() + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = TOKEN_TYPE + " " + new String(encodedBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizations);
        return headers;
    }
}
