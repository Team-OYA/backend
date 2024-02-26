package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.PopupDetailMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSearchMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
public interface PopupMapper {

    Optional<PopupMapperResponse> findById(Long popupId);

    Optional<PopupDetailMapperResponse> findByIdWithDate(PopupDetailMapperRequest request);

    List<PopupMapperResponse> findAllByPlanId(Long planId);

    List<PopupDetailMapperResponse> findAll(PopupSearchMapperRequest request);

    List<PopupDetailMapperResponse> findInProgress(PopupSearchMapperRequest request);

    List<PopupDetailMapperResponse> findScheduled(PopupSearchMapperRequest request);

    List<PopupDetailMapperResponse> findCollections(PopupSearchMapperRequest request);

    List<PopupDetailMapperResponse> findAllRecommended(PopupSearchMapperRequest request);

    void save(PopupSaveMapperRequest request);

    void deleteAll();
}
