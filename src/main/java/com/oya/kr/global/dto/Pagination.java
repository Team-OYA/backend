package com.oya.kr.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {

	private int pageNo; /** 페이지 번호 */
	private int amount; /** 한 페이지당 데이터 수 */
}
