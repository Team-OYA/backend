package com.oya.kr.popup.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Plan extends Base {

    private User user;
    private String office;
    private int floor;
    private String location;
    private LocalDateTime openDate;
    private LocalDateTime closeDate;
    private String businessPlanUrl;
    private EntranceStatus entranceStatus;
    private String contactInformation;
    private Category category;
}
