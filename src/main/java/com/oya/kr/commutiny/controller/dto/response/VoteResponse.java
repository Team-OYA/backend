package com.oya.kr.commutiny.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoteResponse {
	private final Long vote_id;
	private final String content;
	private final String voteCreatedDate;
	private final long voteSum;

	private boolean isChecked; // 내가 체크를 했는지 안했는지

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
