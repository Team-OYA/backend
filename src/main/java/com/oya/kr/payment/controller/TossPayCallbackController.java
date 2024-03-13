package com.oya.kr.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oya.kr.payment.service.TossPayService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.03.10
 */
@Controller
@RequestMapping("/api/v1/payments/toss")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TossPayCallbackController {

    private final TossPayService tossPayService;

    @Value("${payment.toss.success-callback}")
    private String successCallback;

    @Value("${payment.toss.fail-callback}")
    private String failCallback;

    /**
     * 토스페이 결제 승인 시 성공 콜백
     *
     * @parameter String, String, String, Long
     * @return String
     * @author 김유빈
     * @since 2024.02.29
     */
    @GetMapping("/success")
    public String success(
        @RequestParam String orderId,
        @RequestParam String paymentType,
        @RequestParam String paymentKey,
        @RequestParam Long amount
    ) {
        Long planId = tossPayService.success(orderId, paymentType, paymentKey, amount);
        return String.format("redirect:%s?planId=%s", successCallback, planId);
    }

    /**
     * 토스페이 결제 에러 시 실패 콜백
     *
     * @return String
     * @author 김유빈
     * @since 2024.02.29
     */
    @GetMapping("/fail")
    public String fail() {
        tossPayService.fail();
        return String.format("redirect:%s", failCallback);
    }
}
