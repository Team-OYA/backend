package com.oya.kr.popup.service;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_PLAN;
import static com.oya.kr.popup.exception.PlanErrorCodeList.PLAN_HAS_POPUP;
import static com.oya.kr.popup.exception.PopupErrorCodeList.NOT_EXIST_POPUP;
import static com.oya.kr.user.exception.UserErrorCodeList.NOT_EXIST_USER;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.S3Connector;
import com.oya.kr.popup.controller.dto.request.PopupSaveRequest;
import com.oya.kr.popup.controller.dto.response.PopupImageResponse;
import com.oya.kr.popup.controller.dto.response.PopupListResponse;
import com.oya.kr.popup.controller.dto.response.PopupResponse;
import com.oya.kr.popup.controller.dto.response.PopupsListResponse;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.PopupImage;
import com.oya.kr.popup.domain.PopupSort;
import com.oya.kr.popup.domain.WithdrawalStatus;
import com.oya.kr.popup.mapper.PlanMapper;
import com.oya.kr.popup.mapper.PopupImageMapper;
import com.oya.kr.popup.mapper.PopupMapper;
import com.oya.kr.popup.mapper.dto.request.PopupImageSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSearchRequest;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;

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

    private final UserMapper userMapper;
    private final PlanMapper planMapper;
    private final PopupMapper popupMapper;
    private final PopupImageMapper popupImageMapper;
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
        PopupMapperResponse popupMapperResponse = popupMapper.findById(popupId)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_POPUP));
        Plan savedPlan = findPlanById(popupMapperResponse.getPlanId());
        return PopupResponse.from(popupMapperResponse.toDomain(savedPlan));
    }

    /**
     * 팝업스토어 게시글 리스트 조회 기능 구현
     *
     * @parameter String, String
     * @return PopupSearchRequest
     * @author 김유빈
     * @since 2024.02.19
     */
    @Transactional(readOnly = true)
    public PopupsListResponse findAll(String email, String sort) {
        PopupSort popupSort = PopupSort.from(sort);

        List<PopupDetailMapperResponse> mapperResponses = new ArrayList<>();
        PopupSearchRequest request = new PopupSearchRequest(WithdrawalStatus.APPROVAL.getName());
        if (popupSort.isAll()) {
            mapperResponses = popupMapper.findAll(request);
        } else if (popupSort.isProgress()) {
            mapperResponses = popupMapper.findInProgress(request);
        } else if (popupSort.isScheduled()) {
            mapperResponses = popupMapper.findScheduled(request);
        }
        return new PopupsListResponse(
            mapperResponses.stream()
                .map(PopupListResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 팝업스토어 게시글 작성 기능 구현
     *
     * @parameter String, PopupSaveRequest
     * @author 김유빈
     * @since 2024.02.19
     */
    public void save(String email, PopupSaveRequest request, MultipartFile thumbnail) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsBusiness();

        Plan savedPlan = findPlanById(request.getPlanId(), savedUser);
        savedPlan.validateEntranceStatusIsApprove();
        validatePlanDoesNotHavePopup(savedPlan);

        Popup popup = Popup.saved(savedPlan, request.getTitle(), request.getDescription());
        PopupSaveMapperRequest popupSaveMapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(popupSaveMapperRequest);

        Popup savedPopup = findPopupById(popupSaveMapperRequest.getPopupId(), savedPlan);
        String thumbnailUrl = s3Connector.save(thumbnail);
        PopupImage popupImage = PopupImage.saved(thumbnailUrl, savedPopup);

        PopupImageSaveMapperRequest popupImageSaveMapperRequest = PopupImageSaveMapperRequest.from(popupImage);
        popupImageMapper.save(popupImageSaveMapperRequest);
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
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsBusiness();
        return new PopupImageResponse(s3Connector.save(image));
    }

    private User findUserByEmail(String email) {
        return userMapper.findByEmail(email)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
            .toDomain();
    }

    private Plan findPlanById(Long id) {
        return planMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_PLAN))
            .toDomain();
    }

    private Plan findPlanById(Long id, User user) {
        return planMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_PLAN))
            .toDomain(user);
    }

    private Popup findPopupById(Long popupId, Plan plan) {
        return popupMapper.findById(popupId)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_POPUP))
            .toDomain(plan);
    }

    private void validatePlanDoesNotHavePopup(Plan plan) {
        if (!popupMapper.findAllByPlanId(plan.getId()).isEmpty()) {
            throw new ApplicationException(PLAN_HAS_POPUP);
        }
    }
}
