package com.oya.kr.popup.domain;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_DEPARTMENT;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

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

    /**
     * code 를 이용하여 적절한 Department 객체 조회
     *
     * @parameter String
     * @return Department
     * @author 김유빈
     * @since 2024.02.16
     */
    public static Department from(String department) {
        return Arrays.stream(values())
            .filter(it -> it.code.equals(department))
            .findFirst()
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_DEPARTMENT));
    }
}
