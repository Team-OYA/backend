package com.oya.kr.popup.controller.dto.response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.oya.kr.popup.domain.enums.EntranceStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EntranceStatusResponses {

    private final List<EntranceStatusResponse> entranceStatus;

    public static EntranceStatusResponses from(EntranceStatus[] entranceStatuses) {
        return new EntranceStatusResponses(
            Arrays.stream(entranceStatuses)
                .map(EntranceStatusResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
