package com.oya.kr.user.service;

import static com.oya.kr.user.exception.UserErrorCodeList.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oya.kr.global.config.RootConfig;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.mapper.UserMapper;

import lombok.extern.java.Log;

/**
 * @author 이상민
 * @since 2024.02.12
 */
@Log
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserMapper userMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@DisplayName("duplicated_Email() : 이메일이 중복되었을 때")
	@Test
	void duplicated_Email(){
		// given
		String email = "testuser@example.com";

		// when
		when(userMapper.duplicatedEmail(email)).thenReturn(1);

		// then
		ApplicationException exception = assertThrows(ApplicationException.class, () -> userService.duplicatedEmail(email));
		assertEquals(EXISTENT_EMAIL.getMessage(), exception.getMessage());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@DisplayName("duplicated_nickname() : 닉네임이 중복되었을 때")
	@Test
	void duplicated_nickname(){
		// given
		String nickname = "TestUser";

		// when
		when(userMapper.duplicatedNickname(nickname)).thenReturn(1);

		// then
		ApplicationException exception = assertThrows(ApplicationException.class, () -> userService.duplicationNickname(nickname));
		assertEquals(EXISTENT_NICKNAME.getMessage(), exception.getMessage());
	}

}
