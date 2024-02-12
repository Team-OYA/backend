package com.oya.kr.global.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Base {

    protected Long id;
    protected LocalDateTime createDate;
    protected LocalDateTime modifiedDate;
    protected boolean deleted;
}
