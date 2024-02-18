package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
public interface PopupMapper {

    Optional<PopupMapperResponse> findById(Long popupId);

    List<PopupMapperResponse> findAllByPlanId(Long planId);

    void save(PopupSaveMapperRequest request);

    void deleteAll();
}
