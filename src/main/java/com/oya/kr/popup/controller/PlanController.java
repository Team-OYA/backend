package com.oya.kr.popup.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.popup.controller.dto.request.PlanSaveRequest;
import com.oya.kr.popup.controller.dto.response.DepartmentFloorsWithCategoriesResponse;
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

    /**
     * 팝업스토어 카테고리 별 층수 리스트 조회 기능 구현
     *
     * @return ResponseEntity<ApplicationResponse<DepartmentFloorsWithCategoriesResponse>>
     * @author 김유빈
     * @since 2024.02.14
     */
    @GetMapping("/floors")
    public ResponseEntity<ApplicationResponse<DepartmentFloorsWithCategoriesResponse>> findAllFloor() {
        DepartmentFloorsWithCategoriesResponse response = planService.findAllFloor();
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 사업계획서 제안 기능 구현
     *
     * @parameter Principal, MultipartFile, PlanSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.14
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(
        Principal principal,
        @RequestPart("file") MultipartFile businessPlan,
        @RequestPart("data") PlanSaveRequest request) {
        planService.save(principal.getName(), request, businessPlan);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    /**
     * 사업계획서 철회 기능 구현
     *
     * @parameter Principal, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.18
     */
    @PostMapping("/{planId}/withdraw")
    public ResponseEntity<ApplicationResponse<Void>> withdraw(Principal principal, @PathVariable Long planId) {
        planService.withdraw(principal.getName(), planId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    /**
     * 사업계획서 대기 기능 구현
     *
     * @parameter Principal, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.18
     */
    @PostMapping("/{planId}/wait")
    public ResponseEntity<ApplicationResponse<Void>> wait(Principal principal, @PathVariable Long planId) {
        planService.wait(principal.getName(), planId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }
}
