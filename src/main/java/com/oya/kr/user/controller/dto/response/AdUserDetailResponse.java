package com.oya.kr.user.controller.dto.response;

import com.oya.kr.user.mapper.dto.response.AdUserDetailMapperResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdUserDetailResponse {

    private final long id;
    private final String nickname;
    private final String email;
    private final String businessRegistrationNumber;
    private final String connectedNumber;

    public static AdUserDetailResponse from(AdUserDetailMapperResponse response) {
        return new AdUserDetailResponse(
            response.getId(),
            response.getNickname(),
            response.getEmail(),
            response.getBusinessRegistrationNumber(),
            response.getConnectedNumber()
        );
    }
}
