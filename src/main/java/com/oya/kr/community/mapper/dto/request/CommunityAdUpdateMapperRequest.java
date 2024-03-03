package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityAdUpdateMapperRequest {

    private final String orderId;
    private final String paymentKey;
}
