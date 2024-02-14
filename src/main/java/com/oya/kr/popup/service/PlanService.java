package com.oya.kr.popup.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.popup.controller.dto.response.DepartmentResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.domain.Department;

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

    /**
     * 현대백화점 지점 리스트 조회 기능 구현
     *
     * @return DepartmentsResponse
     * @author 김유빈
     * @since 2024.02.14
     */
    public DepartmentsResponse findAllDepartment() {
        Department[] departments = Department.values();
        return new DepartmentsResponse(
            Arrays.stream(departments)
                .map(DepartmentResponse::from)
                .collect(Collectors.toUnmodifiableList()));
    }
}
