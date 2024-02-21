package com.oya.kr.popup.repository;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_PLAN;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.mapper.PlanMapper;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanUpdateEntranceStatusMapperRequest;
import com.oya.kr.user.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.21
 */
@Repository
@RequiredArgsConstructor
public class PlanRepository {

    private final PlanMapper planMapper;

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, User
     * @return Plan
     * @author 김유빈
     * @since 2024.02.21
     */
    public Plan findById(Long id, User user) {
        return planMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_PLAN))
            .toDomain(user);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter User
     * @return List<Plan>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<Plan> findAllWithoutPopup(User user) {
        return planMapper.findAllWithoutPopup().stream()
            .map(planMapper -> planMapper.toDomain(user))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Plan
     * @author 김유빈
     * @since 2024.02.21
     */
    public void save(Plan plan) {
        PlanSaveMapperRequest mapperRequest = PlanSaveMapperRequest.from(plan);
        planMapper.save(mapperRequest);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Plan
     * @author 김유빈
     * @since 2024.02.21
     */
    public void updateEntranceStatus(Plan plan) {
        PlanUpdateEntranceStatusMapperRequest mapperRequest = PlanUpdateEntranceStatusMapperRequest.from(plan);
        planMapper.updateEntranceStatus(mapperRequest);
    }
}
