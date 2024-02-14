package com.oya.kr.user.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oya.kr.global.config.RootConfig;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.mapper.dto.request.SignupUserMapperRequest;

import lombok.extern.java.Log;

@Log
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
class UserMapperTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 사용자 로그인 테스트
	 *
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@Test
	void insertUser() {
		// Given
		JoinRequest joinRequest = new JoinRequest(
			"TestUser",
			"testuser@example.com",
			"password123",
			"19991026",
			0,
			1,
			null,
			null
		);
		SignupUserMapperRequest signupUserMapperRequest = new SignupUserMapperRequest(bCryptPasswordEncoder,
			joinRequest);

		// When
		int result = userMapper.insertUser(signupUserMapperRequest);

		// Then
		assertNotNull(signupUserMapperRequest);
	}
}