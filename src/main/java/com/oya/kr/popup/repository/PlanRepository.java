package com.oya.kr.popup.repository;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_PLAN;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.EntranceStatus;
import com.oya.kr.popup.mapper.PlanMapper;
import com.oya.kr.popup.mapper.dto.request.AllPlanMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanAboutMeMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanUpdateEntranceStatusMapperRequest;
import com.oya.kr.popup.mapper.dto.response.AllPlanMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PlanAboutMeMapperResponse;
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
     * 나의 사업계획서 리스트 조회 기능 구현
     *
     * @parameter Long, Category, EntranceStatus, int, int
     * @return List<PlanAboutMeMapperResponse>
     * @author 김유빈
     * @since 2024.02.27
     */
    public List<PlanAboutMeMapperResponse> findAllAboutMe(
        Long userId, Category category, EntranceStatus entranceStatus, int pageNo, int amount) {
        return planMapper.findAllAboutMe(
            new PlanAboutMeMapperRequest(userId, category.getCode(), entranceStatus.getName(), pageNo, amount));
    }

    /**
     * 모든 사업계획서 리스트 조회 기능 구현
     *
     * @parameter Category, EntranceStatus, int, int
     * @return List<AllPlanMapperResponse>
     * @author 김유빈
     * @since 2024.02.27
     */
    public List<AllPlanMapperResponse> findAll(
        Category category, EntranceStatus entranceStatus, int pageNo, int amount) {
        return planMapper.findAll(
            new AllPlanMapperRequest(category.getCode(), entranceStatus.getName(), pageNo, amount));
    }

    /**
     * 나의 사업계획서 리스트 조회 시 total 개수 조회 기능 구현
     *
     * @parameter Long, Category, EntranceStatus
     * @return int
     * @author 김유빈
     * @since 2024.02.27
     */
    public int countAboutMe(Long userId, Category category, EntranceStatus entranceStatus) {
        return planMapper.countAboutMe(new PlanAboutMeMapperRequest(userId, category.getCode(), entranceStatus.getName(), 0, 0));
    }

    /**
     * 모든 사업계획서 리스트 조회 시 total 개수 조회 기능 구현
     *
     * @parameter Category, EntranceStatus
     * @return int
     * @author 김유빈
     * @since 2024.02.27
     */
    public int countAboutAll(Category category, EntranceStatus entranceStatus) {
        return planMapper.countAboutAll(new AllPlanMapperRequest(category.getCode(), entranceStatus.getName(), 0, 0));
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

    /**
     * 나의 사업계획서 조회 구현
     *
     * @parameter String, Long
     * @author 이상민
     * @since 2024.02.29
     */
    public Plan findById(Long id) {
        return planMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_PLAN))
            .toDomain();
    }
}
