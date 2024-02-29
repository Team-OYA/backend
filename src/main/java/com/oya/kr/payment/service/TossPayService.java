package com.oya.kr.payment.service;

import static com.oya.kr.payment.exception.PaymentErrorCodeList.ALREADY_PAY;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.FAIL;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.INVALID_ORDER_ID;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.NOT_EQUAL_AMOUNT;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.repository.CommunityRepository;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.payment.controller.dto.request.TossPayConfirmRequest;
import com.oya.kr.payment.support.PaymentConnector;
import com.oya.kr.payment.support.dto.request.PaymentRequest;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.community.mapper.dto.response.CommunityAdMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupAdMapperResponse;
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
     * 토스페이 결제 정보 저장
     *
     * @parameter String, TossPayConfirmRequest
     * @author 김유빈
     * @since 2024.02.29
     */
    public void confirm(String email, TossPayConfirmRequest request) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        if (request.getPostType().equals("popup")) {
            Popup savedPopup = popupRepository.findById(request.getPostId(), null);
            popupRepository.saveAd(savedPopup, request.getOrderId(), request.getAmount());
        } else {
            CommunityBasicMapperResponse savedCommunity = communityRepository.findById(request.getPostId());
            communityRepository.saveAd(savedCommunity, request.getOrderId(), request.getAmount());
        }
    }

    /**
     * 토스페이 결제 처리
     *
     * @parameter String, String, String, Long
     * @author 김유빈
     * @since 2024.02.29
     */
    public void success(String orderId, String paymentType, String paymentKey, Long amount) {
        ResponseEntity<Void> response = tossPayConnector.success(new PaymentRequest(paymentKey, orderId, amount));
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ApplicationException(FAIL);
        }
        Optional<PopupAdMapperResponse> popupAdvertisement = popupRepository.findAdByOrderId(orderId);
        if (popupAdvertisement.isPresent()) {
            if (popupAdvertisement.get().getPaymentKey() != null) {
                throw new ApplicationException(ALREADY_PAY);
            }
            if (popupAdvertisement.get().getAmount() != amount) {
                throw new ApplicationException(NOT_EQUAL_AMOUNT);
            }
            popupRepository.updateAdPaymentKey(orderId, paymentKey);
        } else {
            Optional<CommunityAdMapperResponse> communityAdvertisement = communityRepository.findAdByOrderId(orderId);
            if (communityAdvertisement.isEmpty()) {
                throw new ApplicationException(INVALID_ORDER_ID);
            }
            if (communityAdvertisement.get().getPaymentKey() != null) {
                throw new ApplicationException(ALREADY_PAY);
            }
            if (communityAdvertisement.get().getAmount() != amount) {
                throw new ApplicationException(NOT_EQUAL_AMOUNT);
            }
            communityRepository.updateAdPaymentKey(orderId, paymentKey);
        }
        // todo: redirect
    }

    /**
     * 토스페이 결제 실패 안내
     *
     * @author 김유빈
     * @since 2024.02.29
     */
    public void fail() {
        throw new ApplicationException(FAIL);
    }
}
