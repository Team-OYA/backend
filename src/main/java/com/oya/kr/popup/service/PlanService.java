package com.oya.kr.popup.service;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_DEPARTMENT;
import static com.oya.kr.user.exception.UserErrorCodeList.NOT_EXIST_USER;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.controller.dto.request.PlanSaveRequest;
import com.oya.kr.popup.controller.dto.response.DepartmentCategoryResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentFloorsWithCategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.domain.Department;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.mapper.PlanMapper;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
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
     * 사업계획서 제안 기능 구현
     *
     * @parameter String, PlanSaveRequest, MultipartFile
     * @author 김유빈
     * @since 2024.02.16
     */
    public void save(String email, PlanSaveRequest request, MultipartFile businessPlan) {
        User savedUser = findByEmail(email);
        validateUserIsBusiness(savedUser);

        String businessPlanUrl = null;

        Plan plan = Plan.saved(savedUser, request.getOffice(), request.getFloor(), request.getOpenDate(), request.getCloseDate(),
            businessPlanUrl, request.getContactInformation(), request.getCategory());
        PlanSaveMapperRequest mapperRequest = PlanSaveMapperRequest.from(plan);
        planMapper.save(mapperRequest);
    }

    private void validateUserIsBusiness(User savedUser) {
        if (!savedUser.isBusiness()) {
            throw new ApplicationException(NOT_EXIST_DEPARTMENT);
        }
    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
            .toDomain();
    }
}
