package com.oya.kr.user.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicMapperResponse {

	private final Long userId;
	private final String nickname;
	private final String email;
	private final LocalDate birthDate;
	private final String gender;
	private final String registrationType;
	private final String userType;
	private final String profileUrl;
	private final LocalDateTime userCreatedDate;
	private final LocalDateTime userModifiedDate;
	private final boolean userDeleted;
	private final int communityCount;
}
