package com.oya.kr.global.support;

import com.oya.kr.global.support.dto.request.SenderRequest;

/**
 * @author 김유빈
 * @since 2024.02.28
 */
public interface MailSender {

    void send(SenderRequest request);
}
