package com.oya.kr.config.jwt;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oya.kr.global.config.RootConfig;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.JwtProperties;
import com.oya.kr.global.jwt.TokenProvider;

import lombok.extern.java.Log;

/**
 * @author 이상민
 * @since 2024.02.14
 */
@Log
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
public class TokenProviderTest {

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private JwtProperties jwtProperties;

	/**
	 * @author 이상민
	 * @since 2024.02.14
	 */
	@DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
	@Test
	void generateToken() {
		// given
		// JoinRequest joinRequest = new JoinRequest("test", "test@ggg.com", "pw", "991026",);

		// User user = new User("test", "test@ggg.com", "pw","");

		// when
		// String token = tokenProvider.generateToken(user, Duration.ofDays(14));
		//
		// // then
		// Long userId = Jwts.parser()
		// 	.setSigningKey(jwtProperties.getSecretkey())
		// 	.parseClaimsJws(token)
		// 	.getBody()
		// 	.get("id", Long.class);
		//
		// assertEquals(userId, user.getId());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.14
	 */
	@DisplayName("validToken_invalidToken() : 만료된 토큰일 때 유효성 검증에 실패한다.")
	@Test
	void validToken_invalidToken() {
		// given
		String token = JwtFactory.builder()
			.expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
			.build()
			.createToken(jwtProperties);

		// when & then
		assertThatThrownBy(() -> tokenProvider.validToken(token))
			.isInstanceOf(ApplicationException.class);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.14
	 */
	@DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
	@Test
	void getAuthentication() {
		// given
		String email = "user@email.com";
		String token = JwtFactory.builder()
			.subject(email)
			.build()
			.createToken(jwtProperties);

		// when
		Authentication authentication = tokenProvider.getAuthentication(token);

		// then
		assertEquals(((UserDetails)authentication.getPrincipal()).getUsername(), email);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.14
	 */
	@DisplayName("getUserId() : 토큰으로 유저 ID를 가져올 수 있다.")
	@Test
	void getUserId() {
		// given
		Long userId = 1L;
		String token = JwtFactory.builder()
			.claims(Map.of("id", userId))
			.build()
			.createToken(jwtProperties);

		// when
		Long userIdByToken = tokenProvider.getUserId(token);

		// then
		assertEquals(userIdByToken, userId);
	}

}
