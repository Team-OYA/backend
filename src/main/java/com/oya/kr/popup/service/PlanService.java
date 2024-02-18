package com.oya.kr.popup.service;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_PLAN;
import static com.oya.kr.user.exception.UserErrorCodeList.NOT_EXIST_USER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.popup.controller.dto.request.PlanSaveRequest;
import com.oya.kr.popup.controller.dto.response.DepartmentCategoryResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentFloorsWithCategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.controller.dto.response.PlanResponse;
import com.oya.kr.popup.controller.dto.response.PlansResponse;
import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.domain.Department;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.mapper.PlanMapper;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanUpdateEntranceStatusMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PlanMapperResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanService {

    private final UserMapper userMapper;
    private final PlanMapper planMapper;
    private final StorageConnector s3Connector;

    /**
     * 현대백화점 지점 리스트 조회 기능 구현
     *
     * @return DepartmentsResponse
     * @author 김유빈
     * @since 2024.02.14
     */
    @Transactional(readOnly = true)
    public DepartmentsResponse findAllDepartment() {
        Department[] departments = Department.values();
        return new DepartmentsResponse(
            Arrays.stream(departments)
                .map(DepartmentResponse::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    /**
     * 팝업스토어 카테고리 별 층수 리스트 조회 기능 구현
     *
     * @return DepartmentFloorsWithCategoriesResponse
     * @author 김유빈
     * @since 2024.02.14
     */
    @Transactional(readOnly = true)
    public DepartmentFloorsWithCategoriesResponse findAllFloor() {
        Category[] categories = Category.values();
        return new DepartmentFloorsWithCategoriesResponse(
            Arrays.stream(categories)
                .map(DepartmentCategoryResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 팝업스토어 게시글이 없는 사업계획서 리스트 조회 기능 구현
     *
     * @parameter String
     * @return PlansResponse
     * @author 김유빈
     * @since 2024.02.19
     */
    @Transactional(readOnly = true)
    public PlansResponse findAll(String email) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsBusiness();

        List<PlanMapperResponse> planMapperResponses = planMapper.findAllWithoutPopup();

        return new PlansResponse(
            planMapperResponses.stream()
                .map(planMapper -> planMapper.toDomain(savedUser))
                .map(PlanResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 사업계획서 제안 기능 구현
     *
     * @parameter String, PlanSaveRequest, MultipartFile
     * @author 김유빈
     * @since 2024.02.16
     */
    public void save(String email, PlanSaveRequest request, MultipartFile businessPlan) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsBusiness();

        String businessPlanUrl = s3Connector.save(businessPlan);

        Plan plan = Plan.saved(savedUser, request.getOffice(), request.getFloor(), request.getOpenDate(), request.getCloseDate(),
            businessPlanUrl, request.getContactInformation(), request.getCategory());
        PlanSaveMapperRequest mapperRequest = PlanSaveMapperRequest.from(plan);
        planMapper.save(mapperRequest);
    }

    /**
     * 사업계획서 철회 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void withdraw(String email, Long planId) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsBusiness();

        Plan savedPlan = findPlanById(planId, savedUser);
        savedPlan.withdraw(savedUser);
        PlanUpdateEntranceStatusMapperRequest mapperRequest = PlanUpdateEntranceStatusMapperRequest.from(savedPlan);
        planMapper.updateEntranceStatus(mapperRequest);
    }

    /**
     * 사업계획서 대기 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void wait(String email, Long planId) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsAdministrator();

        Plan savedPlan = findPlanById(planId, savedUser);
        savedPlan.waiting();
        PlanUpdateEntranceStatusMapperRequest mapperRequest = PlanUpdateEntranceStatusMapperRequest.from(savedPlan);
        planMapper.updateEntranceStatus(mapperRequest);
    }

    /**
     * 사업계획서 승인 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void approve(String email, Long planId) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsAdministrator();

        Plan savedPlan = findPlanById(planId, savedUser);
        savedPlan.approve();
        PlanUpdateEntranceStatusMapperRequest mapperRequest = PlanUpdateEntranceStatusMapperRequest.from(savedPlan);
        planMapper.updateEntranceStatus(mapperRequest);
    }

    /**
     * 사업계획서 거절 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void deny(String email, Long planId) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsAdministrator();

        Plan savedPlan = findPlanById(planId, savedUser);
        savedPlan.deny();
        PlanUpdateEntranceStatusMapperRequest mapperRequest = PlanUpdateEntranceStatusMapperRequest.from(savedPlan);
        planMapper.updateEntranceStatus(mapperRequest);
    }

    public User findUserByEmail(String email) {
        return userMapper.findByEmail(email)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
            .toDomain();
    }

    public Plan findPlanById(Long id, User user) {
        return planMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_PLAN))
            .toDomain(user);
    }
}
