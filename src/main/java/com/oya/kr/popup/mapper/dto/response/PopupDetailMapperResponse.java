package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupDetailMapperResponse {

    private final long id;
    private final String title;
    private final String content;
    private final String description;
    private final LocalDateTime pulledDate;
    private final String withdrawalStatus;
    private final long planId;
    private final String thumbnail;
    private final Long userId;
    private final String category;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final boolean collected;
}
