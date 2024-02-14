package com.oya.kr.user.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.oya.kr.user.domain.User;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserMapperResponse {

	private final Long id;
	private final String nickname;
	private final String email;
	private final String password;
	private final LocalDate birthDate;
	private final String gender;
	private final String registrationType;
	private final String userType;
	private final String businessRegistrationNumber;
	private final String profileUrl;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
	private final boolean deleted;


	public User toModel(){
		return User.builder()
			.id(this.id)
			.createdDate(this.createdDate)
			.modifiedDate(this.modifiedDate)
			.deleted(this.deleted)
			.nickname(this.nickname)
			.email(this.email)
			.password(this.password)
			.birthDate(this.birthDate)
			.gender(Gender.findByName(this.gender))
			.registrationType(RegistrationType.findByName(this.registrationType))
			.userType(UserType.findByName(this.userType))
			.businessRegistrationNumber(this.businessRegistrationNumber)
			.profileUrl(this.profileUrl)
			.build();
	}
}
