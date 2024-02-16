package com.oya.kr.popup.mapper;

import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
public interface PlanMapper {

    void save(PlanSaveMapperRequest request);

    void deleteAll();
}
