package com.oya.kr.global.support.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatGPTRequest {

    private final String model;
    private final List<ChatGPTMessageRequest> messages;
}
