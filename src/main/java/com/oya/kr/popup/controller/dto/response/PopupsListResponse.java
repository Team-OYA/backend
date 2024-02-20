package com.oya.kr.popup.controller.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupsListResponse {

    private final List<PopupListResponse> popups;

    public static PopupsListResponse from(List<PopupDetailMapperResponse> mapperResponses) {
        return new PopupsListResponse(
            mapperResponses.stream()
                .map(PopupListResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
