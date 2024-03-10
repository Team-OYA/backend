package com.oya.kr.payment.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.dto.response.ApplicationResponse;
import com.oya.kr.payment.controller.dto.request.TossPayConfirmRequest;
import com.oya.kr.payment.service.TossPayService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.28
 */
@RestController
@RequestMapping("/api/v1/payments/toss")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TossPayController {

    private final TossPayService tossPayService;

    /**
     * 토스페이 결제 가능 여부 검증 기능 구현
     *
     * @parameter Principal, TossPayConfirmRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.28
     */
    @PostMapping("/confirm")
    public ResponseEntity<ApplicationResponse<Void>> confirm(
        Principal principal,
        @RequestPart(value = "file", required = false) MultipartFile mainImage,
        @RequestPart("data") TossPayConfirmRequest request) {
        tossPayService.confirm(principal.getName(), request, mainImage);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }
}
