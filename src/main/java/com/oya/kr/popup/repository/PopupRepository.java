package com.oya.kr.popup.repository;

import static com.oya.kr.popup.exception.PopupErrorCodeList.NOT_EXIST_POPUP;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.PopupImage;
import com.oya.kr.popup.domain.enums.PopupSort;
import com.oya.kr.popup.domain.enums.WithdrawalStatus;
import com.oya.kr.popup.mapper.PopupCollectionMapper;
import com.oya.kr.popup.mapper.PopupImageMapper;
import com.oya.kr.popup.mapper.PopupMapper;
import com.oya.kr.popup.mapper.PopupViewMapper;
import com.oya.kr.popup.mapper.dto.request.PopupCollectionMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupCollectionSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupImageSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSearchMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupViewCreateOrUpdateMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupCollectionMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;
import com.oya.kr.user.domain.User;

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
    private final PopupCollectionMapper popupCollectionMapper;

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
    public PopupDetailMapperResponse findByIdWithView(Long id, Long userId) {
        countView(userId, id);
        return popupMapper.findByIdWithDate(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_POPUP));
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
     * @parameter PopupSort, int, int
     * @return List<PopupDetailMapperResponse>
     * @author 김유빈
     * @since 2024.02.22
     */
    public List<PopupDetailMapperResponse> findAll(PopupSort popupSort, int pageNo, int amount) {
        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), pageNo, amount);
        return popupSort.selectForSorting(popupMapper, request).get();
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
     * @parameter PopupImage
     * @author 김유빈
     * @since 2024.02.21
     */
    public void saveImage(PopupImage popupImage) {
        PopupImageSaveMapperRequest popupImageSaveMapperRequest = PopupImageSaveMapperRequest.from(popupImage);
        popupImageMapper.save(popupImageSaveMapperRequest);
    }

    /**
     * 팝업스토어 게시글 스크랩 / 스크랩 취소 기능 구현
     *
     * @parameter Popup, User
     * @author 김유빈
     * @since 2024.02.21
     */
    public void collect(Popup popup, User user) {
        Optional<PopupCollectionMapperResponse> popupCollection = popupCollectionMapper.findByPopupIdAndUserId(
            new PopupCollectionMapperRequest(user.getId(), popup.getId()));

        if (popupCollection.isPresent()) {
            PopupCollectionMapperResponse savedPopupCollection = popupCollection.get();
            popupCollectionMapper.deleteById(savedPopupCollection.getId());
        } else {
            popupCollectionMapper.save(new PopupCollectionSaveMapperRequest(user.getId(), popup.getId()));
        }
    }

    private void countView(Long id, Long userId) {
        PopupViewCreateOrUpdateMapperRequest request = new PopupViewCreateOrUpdateMapperRequest(userId, id);
        popupViewMapper.createOrUpdatePopupView(request);
    }
}
