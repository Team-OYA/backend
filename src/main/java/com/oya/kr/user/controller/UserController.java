package com.oya.kr.user.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.user.controller.dto.request.DuplicatedEmailRequest;
import com.oya.kr.user.controller.dto.request.DuplicatedNicknameRequest;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.request.LoginRequest;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	/**
	 * 회원가입 - 사용자(1), 사업체(2), 관리자(3)
	 *
	 * @param joinRequest
	 * @return JoinResponse
	 * @author 이상민
	 * @since 2024.02.12
	 */
	@PostMapping("/join")
	public ResponseEntity<ApplicationResponse<String>> join(@RequestBody JoinRequest joinRequest) {
		userService.signUp(joinRequest);
		return ResponseEntity.ok(ApplicationResponse.success("회원가입 성공"));
	}

	/**
	 * 이메일 중복확인 API
	 *
	 * @parameter DuplicatedEmailRequest
	 * @return String
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/duplicated/email")
	public ResponseEntity<ApplicationResponse<String>> duplicationEmail(
		@RequestBody DuplicatedEmailRequest duplicatedEmailRequest) {
		userService.duplicatedEmail(duplicatedEmailRequest.getEmail());
		return ResponseEntity.ok(ApplicationResponse.success("사용가능한 이메일입니다."));
	}

	/**
	 * 닉네임 중복확인 API
	 *
	 * @parameter DuplicatedNicknameRequest
	 * @return String
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/duplicated/nickname")
	public ResponseEntity<ApplicationResponse<String>> duplicationNickname(
		@RequestBody DuplicatedNicknameRequest duplicatedNicknameRequest) {
		userService.duplicationNickname(duplicatedNicknameRequest.getNickname());
		return ResponseEntity.ok(ApplicationResponse.success("사용가능한 넥네임입니다."));
	}

	/**
	 * 로그인 - JWT 토큰 발급
	 *
	 * @parameter loginRequest
	 * @return JwtTokenResponse
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/login")
	public ResponseEntity<ApplicationResponse<JwtTokenResponse>> login(@RequestBody LoginRequest loginRequest) {
		JwtTokenResponse jwtTokenResponse = userService.login(loginRequest);
		return ResponseEntity.ok(ApplicationResponse.success(jwtTokenResponse));
	}

	/**
	 * 토큰 재발급 (accessToken 가지고 refreshToken 찾아서 accessToken 다시 재발급)
	 *
	 * @header principal
	 * @return JwtTokenResponse
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/users/reissue")
	public ResponseEntity<ApplicationResponse<JwtTokenResponse>> reissue(Principal principal) {
		User user = userService.findByEmail(principal.getName());
		JwtTokenResponse tokenResponse = userService.reissueAccessToken(user, getAccessToken());
		return ResponseEntity.ok(ApplicationResponse.success(tokenResponse));
	}

	/**
	 * 로그아웃 (리프레시 토큰의 기간 만료 처리, 삭제)
	 *
	 * @header principal
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/logout")
	public ResponseEntity<ApplicationResponse<String>> logout(Principal principal) {
		User user = userService.findByEmail(principal.getName());
		userService.logout(user, getAccessToken());
		return ResponseEntity.ok(ApplicationResponse.success("로그아웃되었습니다."));
	}

	private String getAccessToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getCredentials().toString();
	}

	@GetMapping("/mypage")
	public void me(Principal principal) {
		// log.info(principal.getName());
	}

}
