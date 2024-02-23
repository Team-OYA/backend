package com.oya.kr.user.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.global.dto.response.ApplicationResponse;
import com.oya.kr.user.controller.dto.response.UserDetailResponse;
import com.oya.kr.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminController {

	private final UserService userService;

	/**
	 * 사용자 리스트 정보 조회
	 *
	 * @header principal
	 * @author 이상민
	 * @since 2024.02.22
	 */
	@GetMapping
	public ResponseEntity<ApplicationResponse<List<UserDetailResponse>>> readUsers(Principal principal, @RequestParam("type") String type) {
		return ResponseEntity.ok(ApplicationResponse.success(userService.readUsers(type, principal.getName())));
	}
}
