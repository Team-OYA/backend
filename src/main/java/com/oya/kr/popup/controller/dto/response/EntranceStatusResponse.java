package com.oya.kr.popup.controller.dto.response;

import com.oya.kr.popup.domain.enums.EntranceStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EntranceStatusResponse {

    private final String name;
    private final String description;

    public static EntranceStatusResponse from(EntranceStatus status) {
        return new EntranceStatusResponse(
            status.getName(),
            status.getDescription()
        );
    }
}
