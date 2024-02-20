package com.oya.kr.popup.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.popup.controller.dto.request.PlanSaveRequest;
import com.oya.kr.popup.controller.dto.response.DepartmentCategoryResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentFloorsWithCategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.controller.dto.response.PlanResponse;
import com.oya.kr.popup.controller.dto.response.PlansResponse;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.Department;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.repository.PlanRepository;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

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

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
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
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();
        return new PlansResponse(
            planRepository.findAllWithoutPopup(savedUser).stream()
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
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        String businessPlanUrl = s3Connector.save(businessPlan);

        Plan plan = Plan.saved(savedUser, request.getOffice(), request.getFloor(), request.getOpenDate(), request.getCloseDate(),
            businessPlanUrl, request.getContactInformation(), request.getCategory());
        planRepository.save(plan);
    }

    /**
     * 사업계획서 철회 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void withdraw(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        Plan savedPlan = planRepository.findById(planId, savedUser);
        savedPlan.withdraw(savedUser);
        planRepository.updateEntranceStatus(savedPlan);
    }

    /**
     * 사업계획서 대기 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void wait(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        Plan savedPlan = planRepository.findById(planId, savedUser);
        savedPlan.waiting();
        planRepository.updateEntranceStatus(savedPlan);
    }

    /**
     * 사업계획서 승인 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void approve(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        Plan savedPlan = planRepository.findById(planId, savedUser);
        savedPlan.approve();
        planRepository.updateEntranceStatus(savedPlan);
    }

    /**
     * 사업계획서 거절 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void deny(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        Plan savedPlan = planRepository.findById(planId, savedUser);
        savedPlan.deny();
        planRepository.updateEntranceStatus(savedPlan);
    }
}
