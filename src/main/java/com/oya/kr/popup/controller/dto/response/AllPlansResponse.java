package com.oya.kr.popup.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AllPlansResponse {

    private final int total;
    private final List<AllPlanResponse> plans;
}
