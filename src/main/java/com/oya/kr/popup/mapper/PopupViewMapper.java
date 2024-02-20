package com.oya.kr.popup.mapper;

import com.oya.kr.popup.mapper.dto.request.PopupViewCreateOrUpdateMapperRequest;

public interface PopupViewMapper {

    void createOrUpdatePopupView(PopupViewCreateOrUpdateMapperRequest request);

    void deleteAll();
}
