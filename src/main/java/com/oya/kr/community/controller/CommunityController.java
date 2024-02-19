package com.oya.kr.community.controller;

import static com.oya.kr.community.exception.CommunityErrorCodeList.*;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.CommunityDetailResponse;
import com.oya.kr.community.controller.dto.response.CommunityResponse;
import com.oya.kr.community.service.CommunityService;
import com.oya.kr.global.dto.ApplicationResponse;
import com.oya.kr.global.dto.Pagination;
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
	 * 커뮤니티 게시글 등록 (type : basic, vote) - 사진 추가
	 *
	 * @param communityRequest, type
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@PostMapping( "/communities")
	public ResponseEntity<ApplicationResponse<String>> save(
		Principal principal,
		@RequestPart("data") CommunityRequest communityRequest,
		@RequestPart("images") List<MultipartFile> images,
		@RequestParam("type") String type) {
		User user = userService.findByEmail(principal.getName());
		if (type.equals("basic")) {
			communityService.saveBasic(user, communityRequest, images);
		} else if (type.equals("vote")) {
			communityService.saveVote(user, communityRequest, images);
		} else {
			throw new ApplicationException(NOT_EXIST_COMMUNITY_TYPE);
		}
		return ResponseEntity.ok(ApplicationResponse.success("게시글이 등록되었습니다."));
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@GetMapping("/communities/{communityId}")
	public ResponseEntity<ApplicationResponse<CommunityDetailResponse>> read(Principal principal,
		@PathVariable long communityId) {
		User user = userService.findByEmail(principal.getName());
		return ResponseEntity.ok(ApplicationResponse.success(communityService.read(user, communityId)));
	}

	/**
	 * 커뮤니티 게시글 삭제
	 *
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

	/**
	 * 커뮤니티 게시글 리스트 조회 (sort : all, business, user)
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@GetMapping("/communities")
	public ResponseEntity<ApplicationResponse<CommunityResponse>> reads(Principal principal,
		@RequestParam("type") String type, Pagination pagination) {
		User user = userService.findByEmail(principal.getName());
		CommunityResponse responses;
		switch (type) {
			case "all":
				responses = communityService.readAll(user, pagination);
				break;
			case "business":
				responses = communityService.readBusinessList(user, pagination);
				break;
			case "user":
				responses = communityService.readUserList(user, pagination);
				break;
			default:
				throw new ApplicationException(NOT_EXIST_COMMUNITY_TYPE);
		}
		return ResponseEntity.ok(ApplicationResponse.success(responses));
	}
}
