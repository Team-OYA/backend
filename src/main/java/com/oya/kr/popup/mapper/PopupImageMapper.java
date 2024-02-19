package com.oya.kr.popup.mapper;

import com.oya.kr.popup.mapper.dto.request.PopupImageSaveMapperRequest;

public interface PopupImageMapper {

    void save(PopupImageSaveMapperRequest request);

    void deleteAll();
}
