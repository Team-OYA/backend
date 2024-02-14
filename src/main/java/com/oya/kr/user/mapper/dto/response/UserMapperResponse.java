package com.oya.kr.user.mapper.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserMapperResponse {

	private final Long id;
	private final String nickname;
	private final String email;
	private final String password;
	private final Date birthDate;
	private final String gender;
	private final String registrationType;
	private final String userType;
	private final String businessRegistrationNumber;
	private final String profileUrl;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
	private final boolean deleted;
}
