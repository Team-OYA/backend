package com.oya.kr.popup.mapper;

import java.util.Optional;

import com.oya.kr.popup.mapper.dto.request.PopupCollectionMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupCollectionSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupCollectionMapperResponse;

public interface PopupCollectionMapper {

    Optional<PopupCollectionMapperResponse> findByPopupIdAndUserId(PopupCollectionMapperRequest request);

    void save(PopupCollectionSaveMapperRequest request);

    void deleteById(Long id);

    void deleteAll();
}
