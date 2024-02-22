package com.oya.kr.user.service;

import static com.oya.kr.user.exception.UserErrorCodeList.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;
import com.oya.kr.user.repository.UserRepository;

/**
 * @author 이상민
 * @since 2024.02.12
 */
class UserServiceTest extends SpringApplicationTest {

	@InjectMocks
	private UserService userService;
	@Mock
	private UserMapper userMapper;
	@Mock
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
	void duplicated_Email() {
		// given
		String email = "testuser@example.com";

		// when
		when(userRepository.duplicatedEmail(email)).thenReturn(1);

		// then
		ApplicationException exception = assertThrows(ApplicationException.class,
			() -> userService.duplicatedEmail(email));
		assertEquals(EXISTENT_EMAIL.getMessage(), exception.getMessage());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@DisplayName("duplicated_nickname() : 닉네임이 중복되었을 때")
	@Test
	void duplicated_nickname() {
		// given
		String nickname = "TestUser";

		// when
		when(userRepository.duplicatedNickname(nickname)).thenReturn(1);

		// then
		ApplicationException exception = assertThrows(ApplicationException.class,
			() -> userService.duplicationNickname(nickname));
		assertEquals(EXISTENT_NICKNAME.getMessage(), exception.getMessage());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@DisplayName("사용자가 존재하지 않으면 에러가 발생한다.")
	@Test
	void findByEmail_doesnt_user() {
		// given
		String notExistEmail = "noexist@gmail.com";

		// when
		when(userRepository.findByEmail(notExistEmail)).thenThrow(new ApplicationException(NOT_EXIST_USER));

		// then
		assertThatThrownBy(() -> userService.findByEmail(notExistEmail))
			.isInstanceOf(ApplicationException.class)
			.hasMessageContaining(NOT_EXIST_USER.getMessage());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@DisplayName("이메일로 사용자 정보를 불러올 수 있다.")
	@Test
	void findByEmail() {
		// given
		JoinRequest joinRequest = JoinRequest.builder()
			.email("example@example.com")
			.nickname("JohnDoe")
			.password("password123")
			.birthDate("19900101")
			.gender(1)
			.userType(3)
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
		SignupAdministratorMapperRequest request = new SignupAdministratorMapperRequest(bCryptPasswordEncoder, joinRequest);
		userMapper.insertUser(request);

		// when
		when(userRepository.findByEmail(request.getEmail())).thenReturn(mock(User.class));

		// then
		assertThatCode(() -> userService.findByEmail(request.getEmail()))
			.doesNotThrowAnyException();
	}
}
