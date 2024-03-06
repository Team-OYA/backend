package com.oya.kr.global.support;

import org.springframework.http.ResponseEntity;

import com.oya.kr.global.support.dto.response.ChatGPTResponse;

/**
 * @author 김유빈
 * @since 2024.03.06
 */
public interface ChatGPTConnector {

    ResponseEntity<ChatGPTResponse> quest(String content);
}
