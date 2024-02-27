package com.oya.kr.popup.mapper;

import java.util.List;
import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.AllPlanMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanAboutMeMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanUpdateEntranceStatusMapperRequest;
import com.oya.kr.popup.mapper.dto.response.AllPlanMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PlanAboutMeMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PlanMapperResponse;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
public interface PlanMapper {

    Optional<PlanMapperResponse> findById(Long id);

    List<PlanMapperResponse> findAllWithoutPopup();

    List<PlanAboutMeMapperResponse> findAllAboutMe(PlanAboutMeMapperRequest request);

    List<AllPlanMapperResponse> findAll(AllPlanMapperRequest request);

    int countAboutMe(PlanAboutMeMapperRequest request);

    int countAboutAll(AllPlanMapperRequest request);

    void save(PlanSaveMapperRequest request);

    void updateEntranceStatus(PlanUpdateEntranceStatusMapperRequest request);

    void deleteAll();
}
