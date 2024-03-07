package com.oya.kr.popup.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.dto.response.ApplicationResponse;
import com.oya.kr.popup.controller.dto.request.PlanSaveRequest;
import com.oya.kr.popup.controller.dto.response.AllPlansResponse;
import com.oya.kr.popup.controller.dto.response.CategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentFloorsWithCategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.controller.dto.response.EntranceStatusResponses;
import com.oya.kr.popup.controller.dto.response.MyPlansResponse;
import com.oya.kr.popup.controller.dto.response.PlansResponse;
import com.oya.kr.popup.controller.dto.response.PopupDetailResponse;
import com.oya.kr.popup.controller.dto.response.UserPlanResponse;
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
     * 카테고리 리스트 조회 기능 구현
     *
     * @parameter Principal
     * @return ResponseEntity<ApplicationResponse<CategoriesResponse>>
     * @author 김유빈
     * @since 2024.02.27
     */
    @GetMapping("/categories")
    public ResponseEntity<ApplicationResponse<CategoriesResponse>> findAllCategories(Principal principal) {
        CategoriesResponse response = planService.findAllCategories(principal.getName());
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 사업계획서 진행 단계 리스트 조회 기능 구현
     *
     * @parameter Principal
     * @return ResponseEntity<ApplicationResponse<EntranceStatusResponses>>
     * @author 김유빈
     * @since 2024.02.27
     */
    @GetMapping("/entranceStatus")
    public ResponseEntity<ApplicationResponse<EntranceStatusResponses>> findAllEntranceStatus(Principal principal) {
        EntranceStatusResponses responses = planService.findAllEntranceStatus(principal.getName());
        return ResponseEntity.ok(ApplicationResponse.success(responses));
    }

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
     * 팝업스토어 게시글이 없는 사업계획서 리스트 조회 기능 구현
     *
     * @parameter Principal
     * @return ResponseEntity<ApplicationResponse<PlansResponse>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @GetMapping("/isNotWritten")
    public ResponseEntity<ApplicationResponse<PlansResponse>> findAllWithoutPopup(Principal principal) {
        PlansResponse response = planService.findAllWithoutPopup(principal.getName());
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 나의 사업계획서 리스트 조회 기능 구현
     *
     * @parameter Principal, String, String, int, int
     * @return ResponseEntity<ApplicationResponse<MyPlansResponse>>
     * @author 김유빈
     * @since 2024.02.27
     */
    @GetMapping("/me")
    public ResponseEntity<ApplicationResponse<MyPlansResponse>> findAllAboutMe(
        Principal principal,
        @RequestParam(defaultValue = "") String category, @RequestParam(defaultValue = "") String entranceStatus,
        @RequestParam int pageNo, @RequestParam int amount) {
        MyPlansResponse response = planService.findAllAboutMe(principal.getName(), category, entranceStatus, pageNo, amount);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 모든 사업계획서 리스트 조회 기능 구현
     *
     * @parameter Principal, String, String, int, int
     * @return ResponseEntity<ApplicationResponse<AllPlansResponse>>
     * @author 김유빈
     * @since 2024.02.27
     */
    @GetMapping
    public ResponseEntity<ApplicationResponse<AllPlansResponse>> findAll(
        Principal principal,
        @RequestParam(defaultValue = "") String category, @RequestParam(defaultValue = "") String entranceStatus,
        @RequestParam int pageNo, @RequestParam int amount) {
        AllPlansResponse response = planService.findAll(principal.getName(), category, entranceStatus, pageNo, amount);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 사업계획서 제안 기능 구현
     *
     * @parameter Principal, MultipartFile, PlanSaveRequest
     * @return ResponseEntity<ApplicationResponse<Long>>
     * @author 김유빈
     * @since 2024.02.14
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Long>> save(
        Principal principal,
        @RequestPart("file") MultipartFile businessPlan,
        @RequestPart("data") PlanSaveRequest request) {
        long id = planService.save(principal.getName(), request, businessPlan);
        return ResponseEntity.ok(ApplicationResponse.success(id));
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

    /**
     * 사업계획서 승인 기능 구현
     *
     * @parameter Principal, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.18
     */
    @PostMapping("/{planId}/approve")
    public ResponseEntity<ApplicationResponse<Void>> approve(Principal principal, @PathVariable Long planId) {
        planService.approve(principal.getName(), planId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    /**
     * 사업계획서 거절 기능 구현
     *
     * @parameter Principal, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.18
     */
    @PostMapping("/{planId}/deny")
    public ResponseEntity<ApplicationResponse<Void>> deny(Principal principal, @PathVariable Long planId) {
        planService.deny(principal.getName(), planId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    /**
     * 나의 사업계획서 조회
     *
     * @parameter Principal, planId
     * @return ResponseEntity<ApplicationResponse<AllPlansResponse>>
     * @author 이상민
     * @since 2024.02.29
     */
    @GetMapping("/{planId}")
    public ResponseEntity<ApplicationResponse<UserPlanResponse>> findById(Principal principal, @PathVariable Long planId) {
        return ResponseEntity.ok(ApplicationResponse.success(planService.findById(planId)));
    }

    /**
     * 사업계획서에 따른 팝업스토어 상세 정보 보기
     *
     * @parameter Principal
     * @return ResponseEntity<ApplicationResponse<PopupDetailResponse>>
     * @author 이상민
     * @since 2024.03.01
     */
    @GetMapping("/{planId}/popup")
    public ResponseEntity<ApplicationResponse<PopupDetailResponse>> myPopup(Principal principal,
        @PathVariable long planId) {
        PopupDetailResponse response = planService.myPopup(principal.getName(),planId);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }
}
