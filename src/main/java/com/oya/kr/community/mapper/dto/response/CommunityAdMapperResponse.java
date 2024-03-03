package com.oya.kr.community.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityAdMapperResponse {

    private final long id;
    private final long amount;
    private final long communityId;
    private final String orderId;
    private final String paymentKey;
}
