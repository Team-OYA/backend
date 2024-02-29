package com.oya.kr.payment.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TossPayConfirmRequest {

    private String orderId;
    private Long postId;
    private String postType;
    private Long amount;
}
