package com.oya.kr.global.support.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ChatGPTResponse {

    private final String id;
    private final String object;
    private final String model;
    private final List<ChatGPTChoiceResponse> choices;
}
