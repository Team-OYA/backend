package com.oya.kr.popup.mapper;

import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
public interface PopupMapper {

    void save(PopupSaveMapperRequest request);

    void deleteAll();
}
