package com.oya.kr.popup.domain.enums;

import lombok.Getter;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@Getter
public enum Department {

    THE_HYUNDAI("DP000001", "the hyundai", "더현대"),
    HYUNDAI("DP000002", "hyundai", "현대백화점"),
    HYUNDAI_OUTLET("DP000003", "hyundai outlet", "현대아울렛"),
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
