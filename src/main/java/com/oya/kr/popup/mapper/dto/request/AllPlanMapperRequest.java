package com.oya.kr.popup.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AllPlanMapperRequest {

    private final String category;
    private final String entranceStatus;
    private final int pageNo;
    private final int amount;
}
