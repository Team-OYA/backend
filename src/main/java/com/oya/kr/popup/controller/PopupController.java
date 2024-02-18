package com.oya.kr.popup.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.popup.controller.dto.request.PopupSaveRequest;
import com.oya.kr.popup.controller.dto.response.PopupImageResponse;
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
     * 팝업스토어 게시글 작성 기능 구현
     *
     * @parameter Principal, PopupSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     * @since 2024.02.19
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(Principal principal, @RequestBody PopupSaveRequest request) {
        popupService.save(principal.getName(), request);
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
}
