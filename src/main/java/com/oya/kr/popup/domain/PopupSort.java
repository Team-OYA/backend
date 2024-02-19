package com.oya.kr.popup.domain;

import static com.oya.kr.popup.exception.PopupErrorCodeList.NOT_EXIST_POPUP_SORT;

import java.util.Arrays;

import com.oya.kr.global.exception.ApplicationException;

import lombok.Getter;

@Getter
public enum PopupSort {

    ALL("all", "모든 팝업스토어 게시글 리스트 조회"),
    PROGRESS("progress", "진행중인 팝업스토어 게시글 리스트 조회"),
    SCHEDULED("scheduled", "예정된 팝업스토어 게시글 리스트 조회"),
    ;

    private final String name;
    private final String description;

    PopupSort(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * name 를 이용하여 적절한 PopupSort 객체 조회
     *
     * @parameter String
     * @return PopupSort
     * @author 김유빈
     * @since 2024.02.19
     */
    public static PopupSort from(String name) {
        return Arrays.stream(values())
            .filter(popupSort -> popupSort.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_POPUP_SORT));
    }

    /**
     * 전체 조회 여부 반환
     *
     * @return boolean
     * @author 김유빈
     * @since 2024.02.19
     */
    public boolean isAll() {
        return this == ALL;
    }

    /**
     * 진행중 조회 여부 반환
     *
     * @return boolean
     * @author 김유빈
     * @since 2024.02.19
     */
    public boolean isProgress() {
        return this == PROGRESS;
    }

    /**
     * 예정 조회 여부 반환
     *
     * @return boolean
     * @author 김유빈
     * @since 2024.02.19
     */
    public boolean isScheduled() {
        return this == SCHEDULED;
    }
}
