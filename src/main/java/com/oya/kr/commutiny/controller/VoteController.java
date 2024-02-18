package com.oya.kr.commutiny.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.commutiny.service.VoteService;
import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController {

	private final VoteService voteService;
	private final UserService userService;

	/**
	 * 투표 체크
	 *
	 * @param voteId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@PostMapping("/{voteId}/check")
	public ResponseEntity<ApplicationResponse<String>> check(Principal principal,
		@PathVariable long voteId) {
		User user = userService.findByEmail(principal.getName());
		voteService.check(user, voteId);
		return ResponseEntity.ok(ApplicationResponse.success("투표를 체크했습니다."));
	}

	/**
	 * 투표 체크 취소
	 *
	 * @param votedId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DeleteMapping("/{votedId}/check")
	public ResponseEntity<ApplicationResponse<String>> checkDelete(Principal principal,
		@PathVariable long votedId) {
		User user = userService.findByEmail(principal.getName());
		voteService.checkDelete(user, votedId);
		return ResponseEntity.ok(ApplicationResponse.success("투표를 취소했습니다."));
	}
}
