package com.oya.kr.global.support.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatGPTMessageRequest {

    private final String role;
    private final String content;
}
