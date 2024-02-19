package com.oya.kr.community.service;

import static com.oya.kr.user.exception.UserErrorCodeList.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.CommunityDetailResponse;
import com.oya.kr.community.controller.dto.response.CommunityResponse;
import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.exception.CommunityErrorCodeList;
import com.oya.kr.community.mapper.CommunityMapper;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.domain.Community;
import com.oya.kr.community.domain.CommunityType;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.global.dto.Pagination;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.domain.enums.UserType;
import com.oya.kr.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CommunityService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final CommunityMapper communityMapper;
	private final UserMapper userMapper;
	private final StorageConnector s3Connector;

	/**
	 * 커뮤니티 게시글(일반, basic) 등록
	 *
	 * @param user,  communityRequest
	 * @param images
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void saveBasic(User user, CommunityRequest communityRequest, List<MultipartFile> images) {
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.BASIC.getName(),
			user.getId(), communityRequest);
		communityMapper.saveBasic(saveBasicMapperRequest);
		saveImage(saveBasicMapperRequest.getPostId(), images);
	}

	/**
	 * 이미지 저장
	 *
	 * @param communityId,  images
	 * @author 이상민
	 * @since 2024.02.19
	 */
	private void saveImage(long communityId, List<MultipartFile> images){
		for (MultipartFile image : images) {
			String imageUrl = s3Connector.save(image);
			communityMapper.saveCommunityImage(imageUrl, communityId);
		}
	}

	/**
	 * 커뮤니티 게시글(투표, vote) 등록
	 *
	 * @param user,  communityRequest
	 * @param images
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void saveVote(User user, CommunityRequest communityRequest, List<MultipartFile> images) {
		SaveBasicMapperRequest request = new SaveBasicMapperRequest(CommunityType.VOTE.getName(), user.getId(),
			communityRequest);
		communityMapper.saveBasic(request);
		long communityId = request.getPostId();
		communityRequest.getVotes().forEach(content ->
			communityMapper.saveVote(new SaveVoteMapperRequest(content, communityId))
		);
		saveImage(communityId, images);
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @param loginUser, communityId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public CommunityDetailResponse read(User loginUser, long communityId) {
		communityMapper.createOrUpdateCommunityView(communityId, loginUser.getId());
		CommunityBasicMapperResponse response = getCommunityResponseOrThrow(communityId);
		if (response.isDeleted()) {
			throw new ApplicationException(CommunityErrorCodeList.DELETED_COMMUNITY);
		}
		return getVoteList(response, loginUser);
	}

	/**
	 * 커뮤니티 id로 CommunityBasicMapperResponse 조회
	 *
	 * @param communityId
	 * @return CommunityBasicMapperResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private CommunityBasicMapperResponse getCommunityResponseOrThrow(long communityId) {
		return communityMapper.getCommunityById(communityId)
			.orElseThrow(() -> new ApplicationException(CommunityErrorCodeList.NOT_EXIST_COMMUNITY));
	}

	/**
	 * CommunityBasicMapperResponse로 CommunityResponse 조회
	 *
	 * @param response, loginUser
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private CommunityDetailResponse getVoteList(CommunityBasicMapperResponse response, User loginUser) {
		User user = getWriteId(response);
		Community community = response.toDomain(user);
		List<String> imageList = communityMapper.findByCommunityId(response.getId());
		List<VoteResponse> voteResponseList = new ArrayList<>();
		if (community.getCommunityType().equals(CommunityType.VOTE)) {
			voteResponseList = addVoteResponse(loginUser, response.getId());
		}
		return CommunityDetailResponse.from(imageList, community, response.getCountView(), voteResponseList);
	}

	/**
	 * 게시글 작성자 조회
	 *
	 * @param response
	 * @return User
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private User getWriteId(CommunityBasicMapperResponse response) {
		return userMapper.findById(response.getWriteId())
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
			.toDomain();
	}

	/**
	 * 투표 상세 리스트 조회
	 *
	 * @param loginUser, communityId
	 * @return List<VoteResponse>
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private List<VoteResponse> addVoteResponse(User loginUser, long communityId) {
		List<VoteResponse> voteResponseList = communityMapper.getVoteInfo(communityId);
		voteResponseList.forEach(VoteResponse -> {
			boolean check = Boolean.parseBoolean(
				communityMapper.checkUserVote(VoteResponse.getVote_id(), loginUser.getId()));
			VoteResponse.setChecked(check);
		});
		return voteResponseList;
	}

	/**
	 * 커뮤니티 게시글 조회
	 *
	 * @param user, communityId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void delete(User user, long communityId) {
		CommunityBasicMapperResponse response = getCommunityResponseOrThrow(communityId);
		if (user.getId() != response.getWriteId()) {
			throw new ApplicationException(CommunityErrorCodeList.INVALID_COMMUNITY);
		}
		communityMapper.delete(communityId);
	}

	/**
	 * 커뮤니티 게시글 리스트 조회
	 *
	 * @param loginUser
	 * @param pagination
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public CommunityResponse readAll(User loginUser, Pagination pagination) {
		List<CommunityBasicMapperResponse> responseList = communityMapper.findByAll(
			new ReadCommunityMapperRequest(false, null, pagination.getPageNo(), pagination.getAmount()));
		return mapToCommunityResponses(loginUser, responseList);
	}

	public CommunityResponse readBusinessList(User loginUser, Pagination pagination) {
		List<CommunityBasicMapperResponse> responseList =
			communityMapper.findByType(
				new ReadCommunityMapperRequest(false, UserType.BUSINESS.getName(), pagination.getPageNo(),
					pagination.getAmount()));
		return mapToCommunityResponses(loginUser, responseList);
	}

	public CommunityResponse readUserList(User loginUser, Pagination pagination) {
		List<CommunityBasicMapperResponse> responseList =
			communityMapper.findByType(
				new ReadCommunityMapperRequest(false, UserType.USER.getName(), pagination.getPageNo(),
					pagination.getAmount()));
		return mapToCommunityResponses(loginUser, responseList);
	}

	private CommunityResponse mapToCommunityResponses(User loginUser,
		List<CommunityBasicMapperResponse> responseList) {
		List<CommunityDetailResponse> communityDetailRespons = new ArrayList<>();
		responseList.forEach(response -> {
			CommunityDetailResponse communityDetailResponse = getVoteList(response, loginUser);
			communityDetailRespons.add(communityDetailResponse);
		});

		return new CommunityResponse(communityDetailRespons);
	}
}
