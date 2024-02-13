package com.oya.kr.popup.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Popup extends Base {

    private Plan plan;
    private String title;
    private String description;
    private LocalDateTime pulledDate;
    private WithdrawalStatus withdrawalStatus;
}
