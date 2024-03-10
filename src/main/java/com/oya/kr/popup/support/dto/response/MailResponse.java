package com.oya.kr.popup.support.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MailResponse {

    private final String title;
    private final String content;
}
