package com.oya.kr.user.service;

import static com.oya.kr.global.jwt.TokenProvider.*;
import static com.oya.kr.user.exception.UserErrorCodeList.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;
import com.oya.kr.user.controller.client.KakaoApiClient;
import com.oya.kr.user.controller.dto.request.AccessTokenRequest;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
import com.oya.kr.user.controller.dto.response.KakaoInfo;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupKakaoRequest;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.14
 */
@Service
@RequiredArgsConstructor
@Transactional
public class KaKaoLoginService {

	private final UserMapper userMapper;
	private final TokenProvider tokenProvider;
	private final KakaoApiClient kakaoApiClient;
	private final UserService userService;

	/**
	 * access Token 으로 카카오 로그인
	 *
	 * @param token
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.02.14
	 */
	public JwtTokenResponse accessTokenLogin(AccessTokenRequest token) {
		KakaoInfo kakaoInfo = kakaoApiClient.requestOauthInfo(token);
		User user = findOrCreateMember(kakaoInfo);

		String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
		String refreshToken = tokenProvider.createRefreshToken(user);
		return new JwtTokenResponse("Bearer ", accessToken, refreshToken);
	}

	/**
	 * users 테이블에 유저 찾아 로그인 (존재하지 않다면 생성)
	 *
	 * @param kakaoInfo
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.26
	 */
	private User findOrCreateMember(KakaoInfo kakaoInfo) {
		return userMapper.findByEmail(kakaoInfo.getEmail())
			.map(UserMapperResponse::toDomain)
			.orElseGet(() -> createUser(kakaoInfo));
	}

	/**
	 * kakaoInfo 로 User 생성
	 *
	 * @param kakaoInfo
	 * @return User
	 * @author 이상민
	 * @since 2024.02.15
	 */
	public User createUser(KakaoInfo kakaoInfo) {
		userService.duplicatedEmail(kakaoInfo.getEmail());
		userService.duplicationNickname(kakaoInfo.getNickname());
		SignupKakaoRequest signupKakaoRequest = new SignupKakaoRequest(kakaoInfo);
		int result = userMapper.insertKakaoUser(signupKakaoRequest);
		if (result == 0) {
			throw new ApplicationException(NOT_RESISTER_USER);
		}
		return userService.findByEmail(kakaoInfo.getEmail());
	}
}
