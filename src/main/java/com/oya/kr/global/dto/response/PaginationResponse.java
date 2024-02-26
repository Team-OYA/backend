package com.oya.kr.global.dto.response;

import lombok.Getter;

@Getter
public class PaginationResponse {

	private int total; // 전체 글의 행의 수
	private int currentPage; // 현재 페이지 번호
	private int totalPages; // 전체 페이지 개수
	private int startPage; // 시작 페이지 번호
	private int endPage; // 종료 페이지 번호
	private int pagingCount; // 페이징의 개수

	public PaginationResponse(int total, int currentPage, int size, int pagingCount) {
		this.total = total;
		this.currentPage = currentPage;
		this.pagingCount = pagingCount;

		if(total == 0) { // select 결과가 없다면...
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else { // select 결과가 있다면...
			// 전체 페이지 개수 구하기(전체 글의 수 / 한 화면에 보여질 행의 수)
			// 정수와 정수의 나눗셈의 결과는 정수이므로 13 / 7 = 1
			totalPages = total / size;
			// 보정해줘야 할 경우는? 나머지가 0보다 클 때
			if(total % size > 0) {
				// 전체페이지수를 1증가 처리
				totalPages++;
			}

			// startPage : '이전 [1] [2] [3] [4] [5] 다음' 일때 1을 의미
			// 공식 : 현재페이지 / 페이징의 개수 * 페이징의 개수 + 1;
			startPage = currentPage / pagingCount * pagingCount + 1;
			// 보정해줘야 할 경우는? 5 / 5 * 5 + 1 => 6 경우처럼
			// 					현재페이지 % 5 == 0 일 때
			if(currentPage % pagingCount == 0) {
				// startPage = startPage - 5(페이징의 개수)
				startPage -= pagingCount;
			}

			// endPage   : '이전 [1] [2] [3] [4] [5] 다음' 일때 5을 의미
			endPage = startPage + pagingCount - 1 ;
			// 보정해줘야 할 경우는? endPage > totalPages 일때
			//					endPage를 totalPages로 바꿔줘야 함
			if(endPage > totalPages) {
				endPage = totalPages;
			}
		}
	}
}
