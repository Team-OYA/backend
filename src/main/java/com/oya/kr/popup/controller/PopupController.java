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
import com.oya.kr.popup.controller.dto.response.PopupsListResponse;
import com.oya.kr.popup.controller.dto.response.PopupResponse;
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
     * @parameter Principal, PaginationRequest, String
     * @return ResponseEntity<ApplicationResponse<PopupsListResponse>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @GetMapping
    public ResponseEntity<ApplicationResponse<PopupsListResponse>> findAll(
        Principal principal,
        PaginationRequest paginationRequest,
        @RequestParam String sort) {
        PopupsListResponse response = popupService.findAll(principal.getName(), paginationRequest, sort);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }

    /**
     * 팝업스토어 게시글 추천 리스트 조회 기능 구현
     *
     * @parameter Principal, PaginationRequest
     * @return ResponseEntity<ApplicationResponse<PopupsListResponse>>
     * @author 김유빈
     * @since 2024.02.20
     */
    @GetMapping("/recommended")
    public ResponseEntity<ApplicationResponse<PopupsListResponse>> findAllRecommended(
        Principal principal, PaginationRequest paginationRequest) {
        PopupsListResponse response = popupService.findAllRecommended(principal.getName(), paginationRequest);
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
}
