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

import com.oya.kr.global.dto.request.PaginationRequest;
import com.oya.kr.global.dto.response.ApplicationResponse;
import com.oya.kr.popup.controller.dto.request.PopupSaveRequest;
import com.oya.kr.popup.controller.dto.response.PopupImageResponse;
import com.oya.kr.popup.controller.dto.response.PopupLankListResponse;
import com.oya.kr.popup.controller.dto.response.PopupsListResponse;
import com.oya.kr.popup.controller.dto.response.PopupResponse;
import com.oya.kr.popup.controller.dto.response.StatisticsResponse;
import com.oya.kr.popup.service.PopupService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
@RestController
@RequestMapping("/api/v1/popups")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PopupController {

    private final PopupService popupService;

    /**
     * 팝업스토어 게시글 상세정보 조회 기능 구현
     *
     * @parameter Principal, Long
     * @return ResponseEntity<ApplicationResponse<PopupResponse>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @GetMapping("/{popupId}")
    public ResponseEntity<ApplicationResponse<PopupResponse>> findById(Principal principal, @PathVariable Long popupId) {
        PopupResponse response = popupService.findById(principal.getName(), popupId);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 팝업스토어 게시글 리스트 조회 기능 구현
     *
     * @parameter Principal, pageNo, amount, String
     * @return ResponseEntity<ApplicationResponse<PopupsListResponse>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @GetMapping
    public ResponseEntity<ApplicationResponse<PopupsListResponse>> findAll(
        Principal principal,
        @RequestParam("pageNo") int pageNo, @RequestParam("amount") int amount,
        @RequestParam String sort) {
        PopupsListResponse response = popupService.findAll(principal.getName(), new PaginationRequest(pageNo, amount), sort);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 팝업스토어 게시글 추천 리스트 조회 기능 구현
     *
     * @parameter Principal, pageNo, amount
     * @return ResponseEntity<ApplicationResponse<PopupsListResponse>>
     * @author 김유빈
     * @since 2024.02.20
     */
    @GetMapping("/recommended")
    public ResponseEntity<ApplicationResponse<PopupsListResponse>> findAllRecommended(
        Principal principal, @RequestParam("pageNo") int pageNo, @RequestParam(value="amount", defaultValue = "10") int amount) {
        PopupsListResponse response = popupService.findAllRecommended(principal.getName(), new PaginationRequest(pageNo, amount));
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 팝업스토어 게시글 작성 기능 구현
     *
     * @parameter Principal, PopupSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(
        Principal principal,
        @RequestPart("file") MultipartFile thumbnail,
        @RequestPart("data") PopupSaveRequest request) {
        popupService.save(principal.getName(), request, thumbnail);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    /**
     * 팝업스토어 게시글 이미지 작성 기능 구현
     *
     * @parameter Principal, MultipartFile
     * @return ResponseEntity<ApplicationResponse<PopupImageResponse>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @PostMapping("/image")
    public ResponseEntity<ApplicationResponse<PopupImageResponse>> saveImage(
        Principal principal,
        @RequestParam("file") MultipartFile image) {
        PopupImageResponse response = popupService.saveImage(principal.getName(), image);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 팝업스토어 게시글 스크랩 / 스크랩 취소 기능 구현
     *
     * @parameter Principal, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.21
     */
    @PostMapping("/{popupId}/collections")
    public ResponseEntity<ApplicationResponse<Void>> collect(Principal principal, @PathVariable Long popupId) {
        popupService.collect(principal.getName(), popupId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    /**
     * 사업체 팝업스토어 현황 조회 기능 구현
     *
     * @parameter Principal
     * @return ResponseEntity<ApplicationResponse<StatisticsResponse>>
     * @author 김유빈
     * @since 2024.02.28
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApplicationResponse<StatisticsResponse>> statistics(Principal principal) {
        StatisticsResponse response = popupService.statistics(principal.getName());
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 팝업스토어 순위
     *
     * @parameter Principal
     * @return ResponseEntity<ApplicationResponse<PopupLankListResponse>>
     * @author 이상민
     * @since 2024.02.29
     */
    @GetMapping("/top")
    public ResponseEntity<ApplicationResponse<PopupLankListResponse>> top(Principal principal) {
        PopupLankListResponse response = popupService.top(principal.getName());
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }
}
