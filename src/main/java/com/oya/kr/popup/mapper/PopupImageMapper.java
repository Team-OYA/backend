package com.oya.kr.popup.mapper;

import java.util.List;

import com.oya.kr.popup.mapper.dto.request.PopupImageSaveMapperRequest;

public interface PopupImageMapper {

    void save(PopupImageSaveMapperRequest request);

    void deleteAll();

	List<String> findById(long popupId);
}
