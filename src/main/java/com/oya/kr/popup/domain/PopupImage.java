package com.oya.kr.popup.domain;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PopupImage extends Base {

    private Long id;
    private String url;
    private Popup popup;

    public PopupImage(String url, Popup popup) {
        super();
        this.url = url;
        this.popup = popup;
    }

    public static PopupImage saved(String url, Popup popup) {
        return new PopupImage(url, popup);
    }
}
