package com.oya.kr.user.service;

import static com.oya.kr.global.jwt.TokenProvider.*;
import static com.oya.kr.user.exception.UserErrorCodeList.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.request.LoginRequest;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.service.dto.JoinRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

	private final UserMapper userMapper;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenProvider tokenProvider;

	/**
	 * 회원가입
	 *
	 * @return MyPageResponse
	 * @author 이상민
	 * @since 2024.02.13
	 */
	public void signUp(JoinRequest joinRequest) {
		duplicatedEmail(joinRequest.getEmail());
		duplicatedEmail(joinRequest.getNickname());

		JoinRequestDto joinRequestDto = new JoinRequestDto(bCryptPasswordEncoder, joinRequest);
		int data = userMapper.insertUser(joinRequestDto);
		if(data < 0){
			throw new ApplicationException(NOT_RESISTER_USER);
		}
	}

	/**
	 * 이메일 중복확인
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	public void duplicatedEmail(String email) {
		Integer count1 = userMapper.duplicatedEmail(email);
		if (count1 != null && count1 > 0) {
			throw new ApplicationException(EXISTENT_EMAIL);
		}
	}

	/**
	 * 닉네임 중복화인
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	public void duplicationNickname(String nickname) {
		Integer count2 = userMapper.duplicatedNickname(nickname);
		if (count2 != null && count2 > 0) {
			throw new ApplicationException(EXISTENT_NICKNAME);
		}
	}


	/**
	 * 로그인
	 *
	 * @return MyPageResponse
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@Transactional(readOnly = true)
	public JwtTokenResponse login(LoginRequest loginRequest) {
		User user = findByEmail(loginRequest.getEmail());
		String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
		String refreshToken = tokenProvider.createRefreshToken(user);
		return new JwtTokenResponse("Bearer ", accessToken, refreshToken);
	}

	public User findByEmail(String email) {
		return userMapper.findByEmail(email)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER));
	}

	/**
	 * 토큰 재발급
	 *
	 * @return MyPageResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	public JwtTokenResponse reissueAccessToken(User user, String accessToken) {
		// accessToken으로 refreshToken 찾기
		String refreshToken = "";
		if(tokenProvider.validToken(refreshToken)){
			String newAccessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
			return new JwtTokenResponse("Bearer ", newAccessToken, refreshToken);
		}
		return null;
	}

	public void logout(User user, String accessToken) {
	}


}
