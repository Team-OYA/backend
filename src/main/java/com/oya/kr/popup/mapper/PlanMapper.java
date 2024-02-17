package com.oya.kr.popup.mapper;

import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanUpdateEntranceStatusMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PlanResponse;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
public interface PlanMapper {

    Optional<PlanResponse> findById(Long id);

    void save(PlanSaveMapperRequest request);

    void updateEntranceStatus(PlanUpdateEntranceStatusMapperRequest request);

    void deleteAll();
}
