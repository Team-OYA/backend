package com.oya.kr.user.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdUserDetailMapperResponse {

    private final long id;
    private final String nickname;
    private final String email;
    private final String businessRegistrationNumber;
    private final String connectedNumber;
}
