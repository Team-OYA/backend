package com.oya.kr.global.support.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ChatGPTChoiceMessageResponse {

    private final String role;
    private final String content;
}
