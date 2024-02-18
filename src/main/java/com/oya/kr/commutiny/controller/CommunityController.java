package com.oya.kr.commutiny.controller;

import static com.oya.kr.commutiny.exception.CommunityErrorCodeList.*;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.commutiny.controller.dto.request.CommunityRequest;
import com.oya.kr.commutiny.controller.dto.response.CommunityResponse;
import com.oya.kr.commutiny.service.CommunityService;
import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommunityController {

	private final CommunityService communityService;
	private final UserService userService;

	/**
	 * 커뮤니티 게시글 등록 (type : basic, vote)
	 *
	 * @param communityRequest, type
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@PostMapping("/communities")
	public ResponseEntity<ApplicationResponse<String>> save(Principal principal,
		@RequestBody CommunityRequest communityRequest, @RequestParam("type") String type) {
		User user = userService.findByEmail(principal.getName());
		if (type.equals("basic")) {
			communityService.saveBasic(user, communityRequest);
		} else if (type.equals("vote")) {
			communityService.saveVote(user, communityRequest);
		}else{
			throw new ApplicationException(NOT_EXIST_COMMUNITY_TYPE);
		}
		return ResponseEntity.ok(ApplicationResponse.success("게시글 등록 성공"));
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @param communityId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@GetMapping("/communities/{communityId}")
	public ResponseEntity<ApplicationResponse<CommunityResponse>> read(Principal principal, @PathVariable long communityId) {
		User user = userService.findByEmail(principal.getName());
		return ResponseEntity.ok(ApplicationResponse.success(communityService.read(user, communityId)));
	}

	/**
	 * 커뮤니티 게시글 삭제
	 *
	 * @param communityId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DeleteMapping("/communities/{communityId}")
	public ResponseEntity<ApplicationResponse<String>> delete(Principal principal, @PathVariable long communityId) {
		User user = userService.findByEmail(principal.getName());
		communityService.delete(user, communityId);
		return ResponseEntity.ok(ApplicationResponse.success("게시글이 삭제되었습니다."));
	}
}
