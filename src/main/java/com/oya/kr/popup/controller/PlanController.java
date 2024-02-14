package com.oya.kr.popup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.service.PlanService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanController {

    private final PlanService planService;

    /**
     * 현대백화점 지점 리스트 조회 기능 구현
     *
     * @return ResponseEntity<ApplicationResponse<DepartmentsResponse>>
     * @author 김유빈
     * @since 2024.02.14
     */
    @GetMapping("/departments")
    public ResponseEntity<ApplicationResponse<DepartmentsResponse>> findAllDepartment() {
        DepartmentsResponse response = planService.findAllDepartment();
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }
}
