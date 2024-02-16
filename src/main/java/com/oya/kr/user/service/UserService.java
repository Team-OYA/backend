package com.oya.kr.user.service;

import static com.oya.kr.global.jwt.TokenProvider.*;
import static com.oya.kr.user.exception.UserErrorCodeList.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.global.domain.Header;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.request.LoginRequest;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserMapper userMapper;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenProvider tokenProvider;

	/**
	 * 회원가입
	 *
	 * @author 이상민
	 * @since 2024.02.13
	 */
	public void signUp(JoinRequest joinRequest) {
		duplicatedEmail(joinRequest.getEmail());
		duplicationNickname(joinRequest.getNickname());

		if(joinRequest.getUserType() !=0 && joinRequest.getUserType() != 1){
			administratorSignup(joinRequest);
		}else{
			SignupAdministratorMapperRequest signupAdministratorMapperRequest = new SignupAdministratorMapperRequest(bCryptPasswordEncoder,
				joinRequest);
			int data = userMapper.insertUser(signupAdministratorMapperRequest);
			if (data != -1) {
				throw new ApplicationException(NOT_RESISTER_USER);
			}
		}
	}

	private void administratorSignup(JoinRequest joinRequest) {
		SignupBasicMapperRequest signupBasicMapperRequest = new SignupBasicMapperRequest(bCryptPasswordEncoder, joinRequest);
		int result = userMapper.insertAdminAndKakaoUser(signupBasicMapperRequest);
		if (result == 0) {
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
		Integer count = userMapper.duplicatedEmail(email);
		if (count != null && count > 0) {
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
		Integer count = userMapper.duplicatedNickname(nickname);
		if (count != null && count > 0) {
			throw new ApplicationException(EXISTENT_NICKNAME);
		}
	}

	/**
	 * 로그인
	 *
	 * @return JwtTokenResponse
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@Transactional(readOnly = true)
	public JwtTokenResponse login(LoginRequest loginRequest) {
		Integer count = userMapper.duplicatedEmail(loginRequest.getEmail());
		if (count == 0) {
			throw new ApplicationException(NOT_EXISTENT_EMAIL);
		}
		User user = findByEmail(loginRequest.getEmail());
		if (!user.checkPassword(bCryptPasswordEncoder, loginRequest.getPassword())) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
		String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
		String refreshToken = tokenProvider.createRefreshToken(user);
		return new JwtTokenResponse(Header.BEARER.getValue(), accessToken, refreshToken);
	}

	/**
	 * 이메일로 유저 정보 불러오기
	 *
	 * @return User
	 * @author 이상민
	 * @since 2024.02.13
	 */
	public User findByEmail(String email) {
		UserMapperResponse userMapperResponse = userMapper.findByEmail(email)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER));
		return userMapperResponse.toDomain();
	}

	/**
	 * 토큰 재발급
	 *
	 * @return JwtTokenResponse
	 * @author 이상민
	 * @since 2024.02.12
	 */
	public JwtTokenResponse reissueAccessToken(User user, String accessToken) {
		// accessToken으로 refreshToken 찾기
		String refreshToken = "";
		if (tokenProvider.validToken(refreshToken)) {
			String newAccessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
			return new JwtTokenResponse(Header.BEARER.getValue(), newAccessToken, refreshToken);
		}
		return null;
	}

	public void logout(User user, String accessToken) {
	}

}
