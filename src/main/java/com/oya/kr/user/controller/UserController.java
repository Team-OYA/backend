package com.oya.kr.user.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.global.dto.response.ApplicationResponse;
import com.oya.kr.user.controller.dto.request.AccessTokenRequest;
import com.oya.kr.user.controller.dto.request.DuplicatedEmailRequest;
import com.oya.kr.user.controller.dto.request.DuplicatedNicknameRequest;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.request.LoginRequest;
import com.oya.kr.user.controller.dto.response.BasicUserResponse;
import com.oya.kr.user.controller.dto.response.JwtTokenResponse;
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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserService userService;

	/**
	 * 회원가입 - 사용자(1), 사업체(2), 관리자(3)
	 *
	 * @param joinRequest
	 * @return ResponseEntity<ApplicationResponse<String>>
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
	 * @return ResponseEntity<ApplicationResponse<String>>
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
	 * @return ResponseEntity<ApplicationResponse<String>>
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
	 * @return ResponseEntity<ApplicationResponse<JwtTokenResponse>>
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
	 * @return ResponseEntity<ApplicationResponse<JwtTokenResponse>>
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/users/reissue")
	public ResponseEntity<ApplicationResponse<JwtTokenResponse>> reissue(Principal principal, @RequestBody AccessTokenRequest accessToken) {
		return ResponseEntity.ok(ApplicationResponse.success(userService.reissueAccessToken(principal.getName(), getAccessToken())));
	}

	/**
	 * 로그아웃
	 *
	 * @header principal
	 * @return ResponseEntity<ApplicationResponse<String>>
	 * @author 이상민
	 * @since 2024.02.13
	 */
	@PostMapping("/logout")
	public ResponseEntity<ApplicationResponse<String>> logout(Principal principal) {
		return ResponseEntity.ok(ApplicationResponse.success(userService.logout(getAccessToken())));
	}

	/**
	 * 사용자 상세조회
	 *
	 * @header principal
	 * @return ResponseEntity<ApplicationResponse<List<? extends BasicUserResponse>>>
	 * @author 이상민
	 * @since 2024.02.22
	 */
	@GetMapping("/admin/users/{userId}")
	public ResponseEntity<ApplicationResponse<?>> read(Principal principal, @PathVariable long userId) {
		return ResponseEntity.ok(ApplicationResponse.success(userService.read(userId)));
	}

	/**
	 * 사용자 리스트
	 *
	 * @header principal
	 * @return ResponseEntity<ApplicationResponse<List<? extends BasicUserResponse>>>
	 * @author 이상민
	 * @since 2024.02.22
	 */
	@GetMapping("/admin/users")
	public ResponseEntity<ApplicationResponse<List<? extends BasicUserResponse>>> reads(Principal principal, @RequestParam("type") String type) {
		return ResponseEntity.ok(ApplicationResponse.success(userService.reads(type)));
	}

	private String getAccessToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getCredentials().toString();
	}
}