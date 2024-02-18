package com.oya.kr.popup.mapper.dto.response;

import java.time.LocalDateTime;

import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.WithdrawalStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupMapperResponse {

    private final long id;
    private final String title;
    private final String description;
    private final LocalDateTime pulledDate;
    private final String withdrawalStatus;
    private final long planId;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final boolean deleted;

    public Popup toDomain(Plan plan) {
        return new Popup(
            id, plan, title, description, pulledDate, WithdrawalStatus.from(withdrawalStatus),
            createdDate, modifiedDate, deleted
        );
    }
}
