package com.oya.kr.popup.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPlansResponse {

    private final int total;
    private final List<MyPlanResponse> plans;
}
