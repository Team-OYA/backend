package com.oya.kr.community.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityAdSaveMapperRequest {

    private long id;
    private final Long communityId;
    private final Long amount;
}
