package com.oya.kr.popup.domain.enums;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_DEPARTMENT_FLOOR;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

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

    /**
     * code 를 이용하여 적절한 DepartmentFloor 객체 조회
     *
     * @parameter String
     * @return DepartmentFloor
     * @author 김유빈
     * @since 2024.02.16
     */
    public static DepartmentFloor from(String floor) {
        return Arrays.stream(values())
            .filter(it -> it.code.equals(floor))
            .findFirst()
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_DEPARTMENT_FLOOR));
    }
}
