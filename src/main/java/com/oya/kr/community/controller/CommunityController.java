package com.oya.kr.community.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.CommunityDetailResponse;
import com.oya.kr.community.controller.dto.response.CommunityResponse;
import com.oya.kr.community.controller.dto.response.StatisticsResponse;
import com.oya.kr.community.service.CommunityService;
import com.oya.kr.global.dto.Pagination;
import com.oya.kr.global.dto.response.ApplicationResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

	private final CommunityService communityService;

	/**
	 * 커뮤니티 게시글 등록 (type : basic, vote) - 사진 추가
	 *
	 * @param communityRequest, type
	 * @return ResponseEntity<ApplicationResponse<String>>
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@PostMapping
	public ResponseEntity<ApplicationResponse<String>> save(
		Principal principal,
		@RequestPart("data") CommunityRequest communityRequest,
		@RequestPart("images") List<MultipartFile> images,
		@RequestParam("type") String communityType) {
		return ResponseEntity.ok(ApplicationResponse.success(communityService.save(communityType, principal.getName(), communityRequest, images)));
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @param communityId
	 * @return ResponseEntity<ApplicationResponse<CommunityDetailResponse>>
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@GetMapping("/{communityId}")
	public ResponseEntity<ApplicationResponse<CommunityDetailResponse>> read(Principal principal,
		@PathVariable long communityId) {
		return ResponseEntity.ok(ApplicationResponse.success(communityService.read(principal.getName(), communityId)));
	}

	/**
	 * 커뮤니티 게시글 삭제
	 *
	 * @param communityId
	 * @return ResponseEntity<ApplicationResponse<String>>
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DeleteMapping("/{communityId}")
	public ResponseEntity<ApplicationResponse<String>> delete(Principal principal, @PathVariable long communityId) {
		return ResponseEntity.ok(ApplicationResponse.success(communityService.delete(principal.getName(), communityId)));
	}

	/**
	 * 커뮤니티 게시글 리스트 조회 (sort : all, business, user, collections)
	 *
	 * @param type, pageNo, amount
	 * @return ResponseEntity<ApplicationResponse<String>>
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@GetMapping
	public ResponseEntity<ApplicationResponse<CommunityResponse>> reads(Principal principal,
		@RequestParam("type") String type, Pagination pagination) {
		return ResponseEntity.ok(ApplicationResponse.success(communityService.reads(type, principal.getName(), pagination)));
	}

	/**
	 * 커뮤니티 게시글 스크랩 & 스크랩 취소
	 *
	 * @param communityId
	 * @return ResponseEntity<ApplicationResponse<String>>
	 * @author 이상민
	 * @since 2024.02.19
	 */
	@GetMapping("/{communityId}/collections")
	public ResponseEntity<ApplicationResponse<String>> saveCollection(Principal principal, @PathVariable long communityId) {
		return ResponseEntity.ok(ApplicationResponse.success(communityService.saveCollection(principal.getName(), communityId)));
	}

	/**
	 * 카테고리 별 커뮤니티 게시글 분석 정보 조회
	 *
	 * @return ResponseEntity<ApplicationResponse<StatisticsResponse>>
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@GetMapping("/statistics")
	public ResponseEntity<ApplicationResponse<StatisticsResponse>> statistics(Principal principal) {
		return ResponseEntity.ok(ApplicationResponse.success(communityService.statistics()));
	}
}
