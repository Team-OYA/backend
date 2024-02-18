package com.oya.kr.popup.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PopupSaveRequest {

    private Long planId;
    private String title;
    private String description;
}
