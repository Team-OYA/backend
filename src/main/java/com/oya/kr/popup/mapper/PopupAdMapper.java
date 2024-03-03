package com.oya.kr.popup.mapper;

import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.PopupAdSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupAdUpdateMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupAdMapperResponse;

public interface PopupAdMapper {

    Optional<PopupAdMapperResponse> findAdByOrderId(String orderId);

    void save(PopupAdSaveMapperRequest request);

    void updateAdPaymentKey(PopupAdUpdateMapperRequest request);
}
