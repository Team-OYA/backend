package com.oya.kr.payment.service;

import static com.oya.kr.payment.exception.PaymentErrorCodeList.ALREADY_PAY;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.FAIL;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.INVALID_ORDER_ID;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.NOT_EQUAL_AMOUNT;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.NOT_EXIST_POPUP_MAIN_IMAGE;
import static com.oya.kr.payment.exception.PaymentErrorCodeList.NOT_EXIST_POST_TYPE;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.community.mapper.dto.response.CommunityBasicWithProfileMapperResponse;
import com.oya.kr.community.repository.CommunityRepository;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.payment.controller.dto.request.TossPayConfirmRequest;
import com.oya.kr.payment.support.PaymentConnector;
import com.oya.kr.payment.support.dto.request.PaymentRequest;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.community.mapper.dto.response.CommunityAdMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupAdMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;
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
    private final StorageConnector s3Connector;

    /**
     * 토스페이 결제 정보 저장
     *
     * @parameter String, TossPayConfirmRequest
     * @author 김유빈
     * @since 2024.02.29
     */
    public void confirm(String email, TossPayConfirmRequest request, MultipartFile mainImage) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        if (request.getPostType().equals("popup")) {
            if (mainImage == null || mainImage.isEmpty()) {
                throw new ApplicationException(NOT_EXIST_POPUP_MAIN_IMAGE);
            }
            String mainImageUrl = s3Connector.save(mainImage);
            Popup savedPopup = popupRepository.findById(request.getPostId(), null);
            popupRepository.saveAd(savedPopup, request.getOrderId(), request.getAmount(), mainImageUrl);
        } else if (request.getPostType().equals("community")) {
            CommunityBasicWithProfileMapperResponse savedCommunity = communityRepository.findById(request.getPostId());
            communityRepository.saveAd(savedCommunity, request.getOrderId(), request.getAmount());
        } else {
            throw new ApplicationException(NOT_EXIST_POST_TYPE);
        }
    }

    /**
     * 토스페이 결제 처리
     *
     * @parameter String, String, String, Long
     * @return Long
     * @author 김유빈
     * @since 2024.02.29
     */
    public Long success(String orderId, String paymentType, String paymentKey, Long amount) {
        ResponseEntity<Void> response = tossPayConnector.success(new PaymentRequest(paymentKey, orderId, amount));
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ApplicationException(FAIL);
        }
        Optional<PopupAdMapperResponse> popupAdvertisement = popupRepository.findAdByOrderId(orderId);
        Long planId = null;
        if (popupAdvertisement.isPresent()) {
            if (popupAdvertisement.get().getPaymentKey() != null) {
                throw new ApplicationException(ALREADY_PAY);
            }
            if (popupAdvertisement.get().getAmount() != amount) {
                throw new ApplicationException(NOT_EQUAL_AMOUNT);
            }
            popupRepository.updateAdPaymentKey(orderId, paymentKey);
            PopupMapperResponse savedPopup = popupRepository.findByIdWithoutUser(popupAdvertisement.get().getPopupId());
            planId = savedPopup.getPlanId();
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
        return planId;
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
