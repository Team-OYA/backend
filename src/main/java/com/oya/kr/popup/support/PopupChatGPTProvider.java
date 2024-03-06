package com.oya.kr.popup.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oya.kr.global.support.ChatGPTProvider;

/**
 * @author 김유빈
 * @since 2024.03.06
 */
@Component
public class PopupChatGPTProvider implements ChatGPTProvider {

    @Value("${chat-gpt.url}")
    private String url;

    @Value("${chat-gpt.secret-key}")
    private String secretKey;

    @Value("${chat-gpt.model}")
    private String model;

    @Value("${chat-gpt.role}")
    private String role;


    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getRole() {
        return role;
    }
}
