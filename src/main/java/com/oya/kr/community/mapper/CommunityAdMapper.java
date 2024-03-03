package com.oya.kr.community.mapper;

import java.util.Optional;

import com.oya.kr.community.mapper.dto.request.CommunityAdSaveMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityAdMapperResponse;
import com.oya.kr.community.mapper.dto.request.CommunityAdUpdateMapperRequest;

public interface CommunityAdMapper {

    Optional<CommunityAdMapperResponse> findAdByOrderId(String orderId);

    void save(CommunityAdSaveMapperRequest request);

    void updateAdPaymentKey(CommunityAdUpdateMapperRequest request);
}
