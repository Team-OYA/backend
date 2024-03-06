package com.oya.kr.popup.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Header;
import com.oya.kr.global.support.ApiConnector;
import com.oya.kr.global.support.ChatGPTConnector;
import com.oya.kr.global.support.dto.request.ChatGPTMessageRequest;
import com.oya.kr.global.support.dto.request.ChatGPTRequest;
import com.oya.kr.global.support.dto.response.ChatGPTResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.03.06
 */
@Component
@RequiredArgsConstructor
public class PopupChatGPTConnector implements ChatGPTConnector {

    private static final String QUERY = "아래 내용 40자 이내로 요약해줘";

    private final PopupChatGPTProvider provider;

    /**
     * 팝업스토어 게시글 내용 요약 요청
     *
     * @parameter ChatGPTRequest
     * @return ResponseEntity<ChatGPTResponse>
     * @author 김유빈
     * @since 2024.03.06
     */
    @Override
    public ResponseEntity<ChatGPTResponse> quest(String content) {
        ChatGPTRequest request = new ChatGPTRequest(
            provider.getModel(), List.of(new ChatGPTMessageRequest(provider.getRole(), String.format("%s\n%s", QUERY, content))));
        HttpEntity<Map<String, Object>> entity = createRequest(request);
        return ApiConnector.post(provider.getUrl(), entity, ChatGPTResponse.class);
    }

    private HttpEntity<Map<String, Object>> createRequest(ChatGPTRequest request) {
        HttpHeaders headers = createHeaders();
        Map<String, Object> body = createBody(request);
        return new HttpEntity<>(body, headers);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("%s %s", Header.BEARER.getValue(), provider.getSecretKey()));
        return headers;
    }

    private Map<String, Object> createBody(ChatGPTRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", request.getModel());
        body.put("messages", request.getMessages().stream()
            .map(message -> {
                Map<String, Object> content = new HashMap<>();
                content.put("role", message.getRole());
                content.put("content", message.getContent());
                return content;
            })
            .collect(Collectors.toUnmodifiableList()));
        return body;
    }
}
