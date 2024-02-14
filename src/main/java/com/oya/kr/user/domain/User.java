package com.oya.kr.user.domain;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Base {

	private String nickname;
	private String email;
	private String password;
	private Date birthDate;
	private Gender gender;
	private RegistrationType registrationType;
	private UserType userType;
	private String businessRegistrationNumber;
	private String profileUrl;

	public User(UserMapperResponse userMapperResponse) {
		this.id = userMapperResponse.getId();
		this.createdDate = userMapperResponse.getCreatedDate();
		this.modifiedDate = userMapperResponse.getModifiedDate();
		this.deleted = userMapperResponse.isDeleted();
		this.nickname = userMapperResponse.getNickname();
		this.email = userMapperResponse.getEmail();
		this.password = userMapperResponse.getPassword();
		this.birthDate = userMapperResponse.getBirthDate();
		this.gender = Gender.findByName(userMapperResponse.getGender());
		this.registrationType = RegistrationType.findByName(userMapperResponse.getRegistrationType());
		this.userType = UserType.findByName(userMapperResponse.getUserType());
		this.businessRegistrationNumber = userMapperResponse.getBusinessRegistrationNumber();
		this.profileUrl = userMapperResponse.getProfileUrl();
	}

	public static String encodePassword(BCryptPasswordEncoder bCryptPasswordEncoder, String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	/**
	 * 비밀번호 확인
	 *
	 * @return boolean
	 * @author 이상민
	 * @since 2024.02.13
	 */
	public boolean checkPassword(PasswordEncoder passwordEncoder, String password) {
		return passwordEncoder.matches(password, this.password);
	}
}
