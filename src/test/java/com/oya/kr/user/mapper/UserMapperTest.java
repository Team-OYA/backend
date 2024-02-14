package com.oya.kr.user.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.mapper.dto.request.SignupUserMapperRequest;

class UserMapperTest extends SpringApplicationTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 회원가입
	 *
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@Test
	void insertUser() {
		// Given
		JoinRequest request = JoinRequest.builder()
			.email("example@example.com")
			.nickname("JohnDoe")
			.password("password123")
			.birthDate("19900101")
			.gender(1)
			.userType(2)
			.businessRegistrationNumber("1234567890")
			.profileUrl("/profile/johndoe.jpg")
			.nameOfCompany("ABC Inc.")
			.nameOfRepresentative("John Doe")
			.dateOfBusinessCommencement("20220101")
			.businessItem("IT Services")
			.connectedNumber("01012345678")
			.faxNumber("0212345678")
			.zipCode("123456")
			.businessAddress("123 Main St, City, Country")
			.build();
		SignupUserMapperRequest signupUserMapperRequest = new SignupUserMapperRequest(bCryptPasswordEncoder,
			request);

		// When
		userMapper.insertUser(signupUserMapperRequest);

		// Then
		assertNotNull(signupUserMapperRequest);
	}
}
