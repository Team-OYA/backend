package com.oya.kr.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.repository.CommunityRepository;
import com.oya.kr.payment.controller.dto.request.TossPayConfirmRequest;
import com.oya.kr.payment.support.PaymentConnector;
import com.oya.kr.payment.support.dto.request.PaymentRequest;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.repository.PopupRepository;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TossPayService {

    private final UserRepository userRepository;
    private final PopupRepository popupRepository;
    private final CommunityRepository communityRepository;
    private final PaymentConnector tossPayConnector;

    /**
     * 토스페이 결제 가능 여부 검증 기능 구현
     *
     * @parameter String, TossPayConfirmRequest
     * @author 김유빈
     * @since 2024.02.29
     */
    public void confirm(String email, TossPayConfirmRequest request) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        String orderId;
        if (request.getPostType().equals("popup")) {
            Popup savedPopup = popupRepository.findById(request.getPostId(), null);
            orderId = "PU" + popupRepository.saveAd(savedPopup, request.getAmount());
        } else {
            CommunityBasicMapperResponse savedCommunity = communityRepository.findById(request.getPostId());
            orderId = "CN" + communityRepository.saveAd(savedCommunity, request.getAmount());
        }

        tossPayConnector.confirm(new PaymentRequest(request.getPaymentKey(), orderId, request.getAmount()));
    }
}
