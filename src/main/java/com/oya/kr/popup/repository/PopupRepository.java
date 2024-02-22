package com.oya.kr.popup.repository;

import static com.oya.kr.popup.exception.PopupErrorCodeList.NOT_EXIST_POPUP;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.PopupImage;
import com.oya.kr.popup.domain.enums.WithdrawalStatus;
import com.oya.kr.popup.mapper.PopupImageMapper;
import com.oya.kr.popup.mapper.PopupMapper;
import com.oya.kr.popup.mapper.PopupViewMapper;
import com.oya.kr.popup.mapper.dto.request.PopupImageSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSearchMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupViewCreateOrUpdateMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.21
 */
@Repository
@RequiredArgsConstructor
public class PopupRepository {

    private final PopupMapper popupMapper;
    private final PopupImageMapper popupImageMapper;
    private final PopupViewMapper popupViewMapper;

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Plan
     * @return Popup
     * @author 김유빈
     * @since 2024.02.21
     */
    public Popup findById(Long id, Plan plan) {
        return popupMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_POPUP))
            .toDomain(plan);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Long
     * @return PopupDetailMapperResponse
     * @author 김유빈
     * @since 2024.02.21
     */
    public PopupDetailMapperResponse findByIdWithDate(Long id, Long userId) {
        PopupDetailMapperResponse response = popupMapper.findByIdWithDate(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_POPUP));
        countView(id, userId);
        return response;
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long
     * @return List<PopupMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<PopupMapperResponse> findAllByPlanId(Long planId) {
        return popupMapper.findAllByPlanId(planId);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter int, int
     * @return List<PopupDetailMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<PopupDetailMapperResponse> findAll(int pageNo, int amount) {
        return popupMapper.findAll(new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), pageNo, amount));
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter int, int
     * @return List<PopupDetailMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<PopupDetailMapperResponse> findInProgress(int pageNo, int amount) {
        return popupMapper.findInProgress(new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), pageNo, amount));
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter int, int
     * @return List<PopupDetailMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<PopupDetailMapperResponse> findScheduled(int pageNo, int amount) {
        return popupMapper.findScheduled(new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), pageNo, amount));
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter int, int
     * @return List<PopupDetailMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<PopupDetailMapperResponse> findAllRecommended(int pageNo, int amount) {
        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), pageNo, amount);
        return popupMapper.findAllRecommended(request);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Popup, Plan, String
     * @author 김유빈
     * @since 2024.02.21
     */
    public void save(Popup popup, Plan plan, String thumbnailUrl) {
        PopupSaveMapperRequest request = PopupSaveMapperRequest.from(popup);
        popupMapper.save(request);

        Popup savedPopup = findById(request.getPopupId(), plan);
        PopupImage popupImage = PopupImage.saved(thumbnailUrl, savedPopup);
        saveImage(popupImage);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Popup
     * @author 김유빈
     * @since 2024.02.21
     */
    public void saveImage(PopupImage popupImage) {
        PopupImageSaveMapperRequest popupImageSaveMapperRequest = PopupImageSaveMapperRequest.from(popupImage);
        popupImageMapper.save(popupImageSaveMapperRequest);
    }

    private void countView(Long id, Long userId) {
        PopupViewCreateOrUpdateMapperRequest request = new PopupViewCreateOrUpdateMapperRequest(userId, id);
        popupViewMapper.createOrUpdatePopupView(request);
    }
}
