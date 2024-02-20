package com.oya.kr.community.exception;

import org.springframework.http.HttpStatus;

import com.oya.kr.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.19
 */
@RequiredArgsConstructor
public enum CommunityErrorCodeList implements ErrorCode {

	NOT_EXIST_COMMUNITY("C0001", HttpStatus.BAD_REQUEST, "존재하지 않은 커뮤니티 게시글입니다."),
	INVALID_COMMUNITY("C0002", HttpStatus.BAD_REQUEST, "게시글을 삭제할 수 있는 권한이 없습니다."),
	DELETED_COMMUNITY("C0003", HttpStatus.BAD_REQUEST, "삭제된 게시물입니다."),
	NOT_EXIST_COMMUNITY_TYPE("C0004", HttpStatus.BAD_REQUEST, "존재하지 않은 커뮤니티 유형입니다."),

	// vote
	VALID_VOTE("V0001", HttpStatus.BAD_REQUEST, "이미 투표하셨습니다."),
	NOT_EXIST_VOTE("V0002", HttpStatus.BAD_REQUEST, "존재하지 않은 투표입니다."),
	DOES_NOT_EXIST_VOTES("V0003", HttpStatus.BAD_REQUEST, "투표가 존재하지 않습니다."),
	ONLY_ONE_VOTES("V0004", HttpStatus.BAD_REQUEST, "투표가 하나밖에 없습니다."),

	// collection
	FAIL_COMMUNITY_COLLECTION("CC0001", HttpStatus.BAD_REQUEST, "커뮤니티 게시글을 저장할 수 없습니다."),
	;

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public HttpStatus getStatusCode() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
