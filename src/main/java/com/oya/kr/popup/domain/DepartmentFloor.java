package com.oya.kr.popup.domain;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
public enum DepartmentFloor {

    B1("DF000001", "지하 1층"),
    _1F("DF000002", "1층"),
    _5F("DF000003", "5층"),
    ;

    private final String code;
    private final String description;

    DepartmentFloor(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
