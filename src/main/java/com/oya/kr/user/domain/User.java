package com.oya.kr.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Base {

	private String nickname;
	private String email;
	private String password;
	private LocalDate birthDate;
	private Gender gender;
	private RegistrationType registrationType;
	private UserType userType;
	private String businessRegistrationNumber;
	private String profileUrl;

	@Builder
	public User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean deleted,
		String nickname, String email, String password, LocalDate birthDate, Gender gender,
		RegistrationType registrationType, UserType userType, String businessRegistrationNumber, String profileUrl) {
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.deleted = deleted;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.gender = gender;
		this.registrationType = registrationType;
		this.userType = userType;
		this.businessRegistrationNumber = businessRegistrationNumber;
		this.profileUrl = profileUrl;
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
