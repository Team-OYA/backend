package com.oya.kr.global.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaginationRequest {

    private int pageNo;
    private int amount;
}
