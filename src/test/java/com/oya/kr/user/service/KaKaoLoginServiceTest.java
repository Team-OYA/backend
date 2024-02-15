package com.oya.kr.user.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.user.controller.dto.response.KakaoInfo;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;

/**
 * @author 이상민
 * @since 2024.02.15
 */
class KaKaoLoginServiceTest extends SpringApplicationTest {

	@Autowired
	private KaKaoLoginService kaKaoLoginService;

	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@DisplayName("kakaoinfo를 가지고 User를 생성할 수 있다.")
	@Test
	void createUser(){
		// given
		KakaoInfo.KakaoProfile kakaoProfile = new KakaoInfo.KakaoProfile("SampleNickname", "https://example.com/sample_image.jpg");
		KakaoInfo.KakaoAccount kakaoAccount = new KakaoInfo.KakaoAccount(kakaoProfile, "sample.email@example.com");
		KakaoInfo kakaoInfo = new KakaoInfo();
		kakaoInfo.setKakaoAccount(kakaoAccount);

		// when
		User user = kaKaoLoginService.createUser(kakaoInfo);

		// then;
		assertNotNull(user);
		assertEquals("sample.email@example.com", user.getEmail());
		assertEquals("SampleNickname", user.getNickname());
	}

}