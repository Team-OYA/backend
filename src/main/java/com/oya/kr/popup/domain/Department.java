package com.oya.kr.popup.domain;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
public enum Department {

    THE_HYUNDAI("the hyundai", "DP000000", "더현대"),
    HYUNDAI("hyundai", "DP000001", "현대백화점"),
    HYUNDAI_OUTLET("hyundai outlet", "DP000002", "현대아울렛"),
    ;

    private final String code;
    private final String name;
    private final String description;

    Department(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
