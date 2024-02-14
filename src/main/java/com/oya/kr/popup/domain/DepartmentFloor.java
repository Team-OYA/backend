package com.oya.kr.popup.domain;

import lombok.Getter;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@Getter
public enum DepartmentFloor {

    B1("DF000001", "b1", "지하 1층"),
    _1F("DF000002", "1f", "1층"),
    _5F("DF000003", "5f", "5층"),
    ;

    private final String code;
    private final String name;
    private final String description;

    DepartmentFloor(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
