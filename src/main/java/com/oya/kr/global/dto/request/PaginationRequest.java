package com.oya.kr.global.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
public class PaginationRequest {

    private final int pageNo;
    private final int amount;

    public PaginationRequest(int pageNo, int amount) {
        this.pageNo = Math.max(0, pageNo) * amount;
        this.amount = amount;
    }
}
