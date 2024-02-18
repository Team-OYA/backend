package com.oya.kr.popup.service;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_PLAN;
import static com.oya.kr.popup.exception.PlanErrorCodeList.PLAN_HAS_POPUP;
import static com.oya.kr.user.exception.UserErrorCodeList.NOT_EXIST_USER;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.controller.dto.request.PopupSaveRequest;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.mapper.PlanMapper;
import com.oya.kr.popup.mapper.PopupMapper;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
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

    /**
     * 팝업스토어 게시글 작성 기능 구현
     *
     * @parameter String, PopupSaveRequest
     * @author 김유빈
     * @since 2024.02.19
     */
    public void save(String email, PopupSaveRequest request) {
        User savedUser = findUserByEmail(email);
        savedUser.validateUserIsBusiness();

        Plan savedPlan = findPlanById(request.getPlanId(), savedUser);
        savedPlan.validateEntranceStatusIsApprove();
        validatePlanDoesNotHavePopup(savedPlan);

        Popup popup = Popup.saved(savedPlan, request.getTitle(), request.getDescription());
        PopupSaveMapperRequest mapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(mapperRequest);
    }

    private User findUserByEmail(String email) {
        return userMapper.findByEmail(email)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
            .toDomain();
    }

    private Plan findPlanById(Long id, User user) {
        return planMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_PLAN))
            .toDomain(user);
    }

    private void validatePlanDoesNotHavePopup(Plan plan) {
        if (!popupMapper.findAllByPlanId(plan.getId()).isEmpty()) {
            throw new ApplicationException(PLAN_HAS_POPUP);
        }
    }
}
