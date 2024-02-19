package com.oya.kr.community.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.community.service.VoteService;
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
@RequestMapping("/api/v1/votes/{voteId}/check")
public class VoteController {

	private final VoteService voteService;

	/**
	 * 투표 체크
	 *
	 * @param voteId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@PostMapping
	public ResponseEntity<ApplicationResponse<String>> check(Principal principal,
		@PathVariable long voteId) {
		return ResponseEntity.ok(ApplicationResponse.success(voteService.check(principal.getName(), voteId)));
	}

	/**
	 * 투표 체크 취소
	 *
	 * @param voteId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DeleteMapping
	public ResponseEntity<ApplicationResponse<String>> checkDelete(Principal principal,
		@PathVariable long voteId) {
		return ResponseEntity.ok(ApplicationResponse.success(voteService.checkDelete(principal.getName(), voteId)));
	}
}
