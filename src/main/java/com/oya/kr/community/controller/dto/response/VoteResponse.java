package com.oya.kr.community.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
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
