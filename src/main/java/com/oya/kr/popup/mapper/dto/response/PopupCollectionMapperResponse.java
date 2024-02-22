package com.oya.kr.popup.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PopupCollectionMapperResponse {

    private long id;
    private final long userId;
    private final long popupId;
}
