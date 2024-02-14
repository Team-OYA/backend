package com.oya.kr.popup.domain;

import lombok.Getter;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@Getter
public enum Category {

    FOOD("CG000001", "food", "식품"),
    LIVING("CG000002", "living", "생활"),
    STATIONERY("CG000003", "stationery", "문구"),
    COSMETICS("CG000004", "cosmetics", "화장품"),
    CLOTHING("CG000005", "clothing", "의류"),
    K_POP("CG000006", "k-pop", "k-pop"),
    ;

    private final String code;
    private final String name;
    private final String description;

    Category(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
