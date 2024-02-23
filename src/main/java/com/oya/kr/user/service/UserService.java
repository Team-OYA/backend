package com.oya.kr.user.service;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;
import static com.oya.kr.user.exception.UserErrorCodeList.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.global.repository.RedisRepository;
import com.oya.kr.global.domain.Header;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.jwt.TokenProvider;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.request.LoginRequest;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
import com.oya.kr.user.controller.dto.response.UserDetailResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.dto.response.AdminMapperResponse;
import com.oya.kr.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.14
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenProvider tokenProvider;

	private final RedisRepository redisRepository;

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
			int data = userRepository.insertUser(joinRequest);
			if (data != -1) {
				throw new ApplicationException(NOT_RESISTER_USER);
			}
		}
	}

	private void administratorSignup(JoinRequest joinRequest) {
		int result = userRepository.insertAdminAndKakaoUser(joinRequest);
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
		Integer count = userRepository.duplicatedEmail(email);
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
		Integer count = userRepository.duplicatedNickname(nickname);
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
		Integer count = userRepository.duplicatedEmail(loginRequest.getEmail());
		if (count == 0) {
			throw new ApplicationException(NOT_EXISTENT_EMAIL);
		}
		User user = userRepository.findByEmail(loginRequest.getEmail());
		if (!user.checkPassword(bCryptPasswordEncoder, loginRequest.getPassword())) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
		String accessToken = tokenProvider.createAccessToken(user);
		String refreshToken = tokenProvider.createRefreshToken(user);

		// accessToken, refreshToken 저장
		redisRepository.saveData(accessToken, refreshToken);
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
		return userRepository.findByEmail(email);
	}

	/**
	 * 토큰 재발급
	 *
	 * @return JwtTokenResponse
	 * @author 이상민
	 * @since 2024.02.12
	 */
	public JwtTokenResponse reissueAccessToken(String email, String accessToken) {
		User user = userRepository.findByEmail(email);
		String refreshToken = (String) redisRepository.getData(accessToken);
		if (tokenProvider.validToken(refreshToken)) {
			String newAccessToken = tokenProvider.createAccessToken(user);
			redisRepository.deleteData(accessToken);
			redisRepository.saveData(newAccessToken, refreshToken);
			return new JwtTokenResponse(Header.BEARER.getValue(), newAccessToken, refreshToken);
		}else{
			throw new ApplicationException(EXPIRE_REFRESH_TOKEN);
		}
	}

	/**
	 * 로그아웃
	 *
	 * @author 이상민
	 * @since 2024.02.23
	 */
	public String logout(String accessToken) {
		redisRepository.deleteData(accessToken);
		return "로그아웃되었습니다.";
	}

	public List<UserDetailResponse> readUsers(String type, String email) {
		List<AdminMapperResponse> list = userRepository.readUsers();
		return null;
	}


	/**
	 * 사용자 상세조회
	 *
	 * @header userId, email
	 * @return UserDetailResponse
	 * @author 이상민
	 * @since 2024.02.22
	 */
	public UserDetailResponse read(long userId, String email) {
		User loginUser = userRepository.findByEmail(email);
		return null;
	}
}
