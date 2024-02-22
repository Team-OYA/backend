package com.oya.kr.popup.service;

import static com.oya.kr.popup.exception.PlanErrorCodeList.PLAN_HAS_POPUP;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.dto.request.PaginationRequest;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.S3Connector;
import com.oya.kr.popup.controller.dto.request.PopupSaveRequest;
import com.oya.kr.popup.controller.dto.response.PopupImageResponse;
import com.oya.kr.popup.controller.dto.response.PopupResponse;
import com.oya.kr.popup.controller.dto.response.PopupsListResponse;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.enums.PopupSort;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.repository.PlanRepository;
import com.oya.kr.popup.repository.PopupRepository;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PopupService {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PopupRepository popupRepository;
    private final S3Connector s3Connector;

    /**
     * 팝업스토어 게시글 상세정보 조회 기능 구현
     *
     * @parameter String, Long
     * @return PopupResponse
     * @author 김유빈
     * @since 2024.02.19
     */
    @Transactional(readOnly = true)
    public PopupResponse findById(String email, Long popupId) {
        User savedUser = userRepository.findByEmail(email);
        PopupDetailMapperResponse popupMapperResponse = popupRepository.findByIdWithView(savedUser.getId(), popupId);
        return PopupResponse.from(popupMapperResponse);
    }

    /**
     * 팝업스토어 게시글 리스트 조회 기능 구현
     *
     * @parameter String, PaginationRequest, String
     * @return PopupsListResponse
     * @author 김유빈
     * @since 2024.02.19
     */
    @Transactional(readOnly = true)
    public PopupsListResponse findAll(String email, PaginationRequest paginationRequest, String sort) {
        PopupSort popupSort = PopupSort.from(sort);
        List<PopupDetailMapperResponse> mapperResponses = popupRepository.findAll(popupSort,
            paginationRequest.getPageNo(), paginationRequest.getAmount());
        return PopupsListResponse.from(mapperResponses);
    }

    /**
     * 팝업스토어 게시글 추천 리스트 조회 기능 구현
     *
     * @parameter String, PaginationRequest
     * @return PopupsListResponse
     * @author 김유빈
     * @since 2024.02.20
     */
    @Transactional(readOnly = true)
    public PopupsListResponse findAllRecommended(String email, PaginationRequest paginationRequest) {
        List<PopupDetailMapperResponse> mapperResponses = popupRepository.findAllRecommended(
            paginationRequest.getPageNo(), paginationRequest.getAmount()
        );
        return PopupsListResponse.from(mapperResponses);
    }

    /**
     * 팝업스토어 게시글 작성 기능 구현
     *
     * @parameter String, PopupSaveRequest
     * @author 김유빈
     * @since 2024.02.19
     */
    public void save(String email, PopupSaveRequest request, MultipartFile thumbnail) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        Plan savedPlan = planRepository.findById(request.getPlanId(), savedUser);
        savedPlan.validateEntranceStatusIsApprove();
        validatePlanDoesNotHavePopup(savedPlan);

        String thumbnailUrl = s3Connector.save(thumbnail);

        Popup popup = Popup.saved(savedPlan, request.getTitle(), request.getDescription());
        popupRepository.save(popup, savedPlan, thumbnailUrl);
    }

    /**
     * 팝업스토어 게시글 이미지 작성 기능 구현
     *
     * @parameter String, MultipartFile
     * @return PopupImageResponse
     * @author 김유빈
     * @since 2024.02.19
     */
    public PopupImageResponse saveImage(String email, MultipartFile image) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();
        return new PopupImageResponse(s3Connector.save(image));
    }

    /**
     * 팝업스토어 게시글 스크랩 / 스크랩 취소 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.21
     */
    public void collect(String email, Long popupId) {
        User savedUser = userRepository.findByEmail(email);
        Popup savedPopup = popupRepository.findById(popupId, null);
        popupRepository.collect(savedPopup, savedUser);
    }

    private void validatePlanDoesNotHavePopup(Plan plan) {
        if (!popupRepository.findAllByPlanId(plan.getId()).isEmpty()) {
            throw new ApplicationException(PLAN_HAS_POPUP);
        }
    }
}
