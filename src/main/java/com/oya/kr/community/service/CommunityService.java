package com.oya.kr.community.service;

import static com.oya.kr.community.exception.CommunityErrorCodeList.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.CommunityDetailResponse;
import com.oya.kr.community.controller.dto.response.CommunityResponse;
import com.oya.kr.community.controller.dto.response.StatisticsDetailResponse;
import com.oya.kr.community.controller.dto.response.StatisticsResponse;
import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.domain.enums.CommunityType;
import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.domain.Community;
import com.oya.kr.community.mapper.dto.response.StatisticsResponseMapper;
import com.oya.kr.community.repository.CommunityRepository;
import com.oya.kr.community.repository.VoteRepository;
import com.oya.kr.global.dto.request.PaginationRequest;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.domain.enums.UserType;
import com.oya.kr.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Service
@Transactional
public class CommunityService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final CommunityRepository communityRepository;
	private final UserRepository userRepository;
	private final VoteRepository voteRepository;

	private final StorageConnector s3Connector;

	/**
	 * 커뮤니티 게시글(일반, 투표) 등록
	 *
	 * @param email,  communityRequest, images
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String save(String communityType, String email, CommunityRequest communityRequest, List<MultipartFile> images) {
		User loginUser = userRepository.findByEmail(email);
		List<String> imageUrls = s3Connector.saveAll(images);
		long communityId = communityRepository.saveForBasic(CommunityType.from(communityType), loginUser, communityRequest, imageUrls);

		if(communityType.equals("vote")) {
			List<String> votes = communityRequest.getVotes();
			if(votes.isEmpty()){
				throw new ApplicationException(DOES_NOT_EXIST_VOTES);
			}
			if(votes.size() == 1){
				throw new ApplicationException(ONLY_ONE_VOTES);
			}
			communityRepository.saveAllForVote(communityId, votes);
		}
		return "게시글이 등록되었습니다.";
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @param email, communityId
	 * @return CommunityDetailResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public CommunityDetailResponse read(String email, long communityId) {
		User loginUser = userRepository.findByEmail(email);
		CommunityBasicMapperResponse response = communityRepository.findByIdWithView(communityId, loginUser.getId());

		User writeUser = userRepository.findByUserId(response.getWriteId());
		List<String> imageList = communityRepository.findImageById(response.getId());
		Community community = response.toDomain(writeUser);
		int countView = response.getCountView();

		List<VoteResponse> voteResponseList = getVoteList(community.getCommunityType().getName(), loginUser, community.getId());
		return CommunityDetailResponse.from(imageList, community, countView, voteResponseList);
	}

	/**
	 * 투표 정보 조회
	 *
	 * @param type, loginUser, communityId
	 * @return List<VoteResponse>
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private List<VoteResponse> getVoteList(String type, User loginUser, long communityId) {
		if (type.equals(CommunityType.VOTE.getName())) {
			return voteRepository.getVoteList(communityId, loginUser.getId());
		}
		return null;
	}

	/**
	 * 커뮤니티 게시글 삭제
	 *
	 * @param email, communityId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String delete(String email, long communityId) {
		User loginUser = userRepository.findByEmail(email);
		communityRepository.delete(communityId, loginUser.getId());
		return "게시글이 삭제되었습니다.";
	}

	/**
	 * 커뮤니티 게시글 리스트 조회
	 *
	 * @param type, email, pagination
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@Transactional(readOnly = true)
	public CommunityResponse reads(String type,String email, PaginationRequest pagination) {
		User loginUser = userRepository.findByEmail(email);
		List<CommunityBasicMapperResponse> responseList;

		// 전체 리스트 사이즈 찾기
		int sum = communityRepository.findSizeByType(loginUser, type);

		if(type.equals("all")){
			responseList = communityRepository.findAll(null, pagination.getPageNo(), pagination.getAmount());
		}else if(type.equals("collections")){
			responseList = communityRepository.findAllCollections(loginUser.getId(), pagination.getPageNo(), pagination.getAmount());
		}else{
			String userType = UserType.from(type).getName();
			responseList = communityRepository.findByType(userType, pagination.getPageNo(), pagination.getAmount());
		}
		return mapToCommunityResponses(loginUser, responseList, sum);
	}

	private CommunityResponse mapToCommunityResponses(User loginUser, List<CommunityBasicMapperResponse> responseList, int sum) {
		List<CommunityDetailResponse> communityDetailRespons = new ArrayList<>();
		responseList.forEach(response -> {
			CommunityDetailResponse communityDetailResponse = read(loginUser.getEmail(), response.getId());
			communityDetailRespons.add(communityDetailResponse);
		});
		return new CommunityResponse(sum, communityDetailRespons);
	}

	/**
	 * 커뮤니티 게시글 스크랩
	 *
	 * @param email, communityId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.20
	 */
	public String saveCollection(String email, long communityId) {
		User loginUser = userRepository.findByEmail(email);
		communityRepository.findByIdWithView(communityId, loginUser.getId());
		CollectionMapperRequest request = new CollectionMapperRequest(communityId, loginUser.getId());
		boolean success = communityRepository.saveCollections(request);
		if (success) {
			return "스크랩 완료되었습니다.";
		}
		return "스크랩 취소되었습니다.";
	}

	/**
	 * 카테고리 별 커뮤니티 게시글 분석 정보 조회
	 *
	 * @return StatisticsResponse
	 * @author 이상민
	 * @since 2024.02.20
	 */
	public StatisticsResponse statistics() {
		List<StatisticsResponseMapper> response = communityRepository.statistics();
		Map<String, Integer> categoryCounts = response.stream()
			.collect(Collectors.toMap(StatisticsResponseMapper::getCategoryCode, StatisticsResponseMapper::getCount));
		return new StatisticsResponse(
			Arrays.stream(Category.values())
				.map(category -> new StatisticsDetailResponse(category.getCode(), categoryCounts.getOrDefault(category.getCode(), 0)))
				.collect(Collectors.toUnmodifiableList()));
	}
}
