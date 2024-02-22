package com.oya.kr.popup.domain.enums;

import static com.oya.kr.popup.exception.PopupErrorCodeList.NOT_EXIST_POPUP_SORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.popup.mapper.PopupMapper;
import com.oya.kr.popup.mapper.dto.request.PopupSearchMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;

import lombok.Getter;

@Getter
public enum PopupSort {

    ALL("all", "모든 팝업스토어 게시글 리스트 조회"),
    PROGRESS("progress", "진행중인 팝업스토어 게시글 리스트 조회"),
    SCHEDULED("scheduled", "예정된 팝업스토어 게시글 리스트 조회"),
    COLLECTIONS("collections", "스크랩한 팝업스토어 게시글 리스트 조회"),
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
     * sort 방식에 따라 적절한 mapper 메서드 호출
     *
     * @parameter PopupMapper, PopupSearchRequest
     * @return Supplier<List<PopupDetailMapperResponse>>
     * @author 김유빈
     * @since 2024.02.19
     */
    public Supplier<List<PopupDetailMapperResponse>> selectForSorting(PopupMapper popupMapper, PopupSearchMapperRequest request) {
        if (isAll()) {
            return () -> popupMapper.findAll(request);
        }
        if (isProgress()) {
            return () -> popupMapper.findInProgress(request);
        }
        if (isScheduled()) {
            return () -> popupMapper.findScheduled(request);
        }
        if (isCollections()) {
            return () -> popupMapper.findCollections(request);
        }
        return ArrayList::new;
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

    /**
     * 스크랩 조회 여부 반환
     *
     * @return boolean
     * @author 김유빈
     * @since 2024.02.22
     */
    public boolean isCollections() {
        return this == COLLECTIONS;
    }
}
