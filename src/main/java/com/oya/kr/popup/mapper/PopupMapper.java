package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSearchRequest;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
public interface PopupMapper {

    Optional<PopupMapperResponse> findById(Long popupId);

    List<PopupMapperResponse> findAllByPlanId(Long planId);

    List<PopupDetailMapperResponse> findAll(PopupSearchRequest request);

    List<PopupDetailMapperResponse> findInProgress(PopupSearchRequest request);

    List<PopupDetailMapperResponse> findScheduled(PopupSearchRequest request);

    void save(PopupSaveMapperRequest request);

    void deleteAll();
}
