package com.oya.kr.popup.domain.enums;

import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_EXIST_DEPARTMENT_BRANCH;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@Getter
public enum DepartmentBranch {

    THE_HYUNDAI_SEOUL(Department.THE_HYUNDAI, "B00140000", "the hyundai seoul", "더현대 서울"),
    THE_HYUNDAI_DAEGU(Department.THE_HYUNDAI, "B00146000", "the hyundai daegu", "더현대 대구"),

    HYUNDAI_APGUJEONG(Department.HYUNDAI, "B00121000", "hyundai apgujeong", "현대백화점 압구정본점"),
    HYUNDAI_TRADE_CENTER(Department.HYUNDAI, "B00122000", "hyundai trade center", "현대백화점 무역센터점"),
    HYUNDAI_CHEONHO(Department.HYUNDAI, "B00126000", "hyundai cheonho", "현대백화점 천호점"),
    HYUNDAI_SINCHON(Department.HYUNDAI, "B00127000", "hyundai sinchon", "현대백화점 신촌점"),
    HYUNDAI_MIA(Department.HYUNDAI, "B00141000", "hyundai mia", "현대백화점 미아점"),
    HYUNDAI_MOKDONG(Department.HYUNDAI, "B00142000", "hyundai mokdong", "현대백화점 목동점"),
    HYUNDAI_JUNGDONG(Department.HYUNDAI, "B00143000", "hyundai jungdong", "현대백화점 중동점"),
    HYUNDAI_KINTEX(Department.HYUNDAI, "B00145000", "hyundai kintex", "현대백화점 킨텍스"),
    HYUNDAI_D_CUBE_CITY(Department.HYUNDAI, "B00149000", "hyundai d-cube city", "현대백화점 디큐브시티"),
    HYUNDAI_PANGYO(Department.HYUNDAI, "B00148000", "hyundai pangyo", "현대백화점 판교점"),
    HYUNDAI_BUSAN(Department.HYUNDAI, "B00124000", "hyundai busan", "현대백화점 부산점"),
    HYUNDAI_ULSAN(Department.HYUNDAI, "B00129000", "hyundai ulsan", "현대백화점 울산점"),
    HYUNDAI_ULSAN_DONGGU(Department.HYUNDAI, "B00125000", "hyundai ulsan donggu", "현대백화점 울산동구점"),
    HYUNDAI_CHUNGCHEONG(Department.HYUNDAI, "B00147000", "hyundai chungcheong", "현대백화점 충첨점"),

    HYUNDAI_GIMPO(Department.HYUNDAI_OUTLET, "B00172000", "hyundai gimpo", "현대아울렛 김포점"),
    HYUNDAI_SONGDO(Department.HYUNDAI_OUTLET, "B00174000", "hyundai songdo", "현대아울렛 송도점"),
    HYUNDAI_DAEJEON(Department.HYUNDAI_OUTLET, "B00177000", "hyundai daejeon", "현대아울렛 대전점"),
    HYUNDAI_SPACE_1(Department.HYUNDAI_OUTLET, "B00178000", "hyundai SPACE 1", "현대아울렛 SPACE 1"),
    HYUNDAI_DONGDAEMUN(Department.HYUNDAI_OUTLET, "B00173000", "hyundai dongdaemun", "현대아울렛 동대문점"),
    HYUNDAI_GASAN(Department.HYUNDAI_OUTLET, "B00171000", "hyundai gasan", "현대아울렛 가산점"),
    HYUNDAI_GARDEN_FIVE(Department.HYUNDAI_OUTLET, "B00175000", "hyundai garden five", "현대아울렛 가든파이브점"),
    HYUNDAI_DAEGU(Department.HYUNDAI_OUTLET, "B00176000", "hyundai daegu", "현대아울렛 대구점"),
    ;

    private final Department department;
    private final String code;
    private final String name;
    private final String description;

    DepartmentBranch(Department department, String code, String name, String description) {
        this.department = department;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * code 를 이용하여 적절한 DepartmentBranch 객체 조회
     *
     * @parameter String
     * @return DepartmentBranch
     * @author 김유빈
     * @since 2024.02.18
     */
    public static DepartmentBranch from(String branch) {
        return Arrays.stream(values())
            .filter(it -> it.code.equals(branch))
            .findFirst()
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_DEPARTMENT_BRANCH));
    }

    public static List<DepartmentBranch> findAllByDepartment(Department department) {
        return Arrays.stream(values())
            .filter(branch -> branch.department == department)
            .collect(Collectors.toUnmodifiableList());
    }
}
