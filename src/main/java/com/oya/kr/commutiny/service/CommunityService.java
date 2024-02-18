package com.oya.kr.commutiny.service;

import static com.oya.kr.commutiny.exception.CommunityErrorCodeList.*;
import static com.oya.kr.user.exception.UserErrorCodeList.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.commutiny.controller.dto.request.CommunityRequest;
import com.oya.kr.commutiny.controller.dto.response.CommunityResponse;
import com.oya.kr.commutiny.controller.dto.response.VoteResponse;
import com.oya.kr.commutiny.domain.Community;
import com.oya.kr.commutiny.domain.CommunityType;
import com.oya.kr.commutiny.mapper.CommunityMapper;
import com.oya.kr.commutiny.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.commutiny.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.commutiny.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.commutiny.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.global.dto.Pagination;
import com.oya.kr.global.exception.ApplicationException;
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

	/**
	 * 커뮤니티 게시글(일반, basic) 등록
	 *
	 * @param user, communityRequest
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void saveBasic(User user, CommunityRequest communityRequest) {
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.BASIC.getName(),
			user.getId(), communityRequest);
		communityMapper.saveBasic(saveBasicMapperRequest);
	}

	/**
	 * 커뮤니티 게시글(투표, vote) 등록
	 *
	 * @param user, communityRequest
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void saveVote(User user, CommunityRequest communityRequest) {
		SaveBasicMapperRequest request = new SaveBasicMapperRequest(CommunityType.VOTE.getName(), user.getId(),
			communityRequest);
		communityMapper.saveBasic(request);
		long postId = request.getPostId();
		communityRequest.getVotes().forEach(content ->
			communityMapper.saveVote(new SaveVoteMapperRequest(content, postId))
		);
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @param loginUser, communityId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public CommunityResponse read(User loginUser, long communityId) {
		CommunityBasicMapperResponse response = getCommunityResponseOrThrow(communityId);
		if (response.isDeleted()) {
			throw new ApplicationException(DELETED_COMMUNITY);
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
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_COMMUNITY));
	}

	/**
	 * CommunityBasicMapperResponse로 CommunityResponse 조회
	 *
	 * @param response, loginUser
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private CommunityResponse getVoteList(CommunityBasicMapperResponse response, User loginUser) {
		User user = getWriteId(response);
		Community community = response.toDomain(user);
		List<VoteResponse> voteResponseList = new ArrayList<>();
		if (community.getCommunityType().equals(CommunityType.VOTE)) {
			voteResponseList = addVoteResponse(loginUser, response.getId());
		}
		return CommunityResponse.from(community, voteResponseList);
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
			throw new ApplicationException(INVALID_COMMUNITY);
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
	public List<CommunityResponse> readAll(User loginUser, Pagination pagination) {
		List<CommunityBasicMapperResponse> responseList = communityMapper.findByAll(
			new ReadCommunityMapperRequest(false, null, pagination.getPageNo(), pagination.getAmount()));
		return mapToCommunityResponses(loginUser, responseList);
	}

	public List<CommunityResponse> readBusinessList(User loginUser, Pagination pagination) {
		List<CommunityBasicMapperResponse> responseList =
			communityMapper.findByType(
				new ReadCommunityMapperRequest(false, UserType.BUSINESS.getName(), pagination.getPageNo(),
					pagination.getAmount()));
		return mapToCommunityResponses(loginUser, responseList);
	}

	public List<CommunityResponse> readUserList(User loginUser, Pagination pagination) {
		List<CommunityBasicMapperResponse> responseList =
			communityMapper.findByType(
				new ReadCommunityMapperRequest(false, UserType.USER.getName(), pagination.getPageNo(),
					pagination.getAmount()));
		return mapToCommunityResponses(loginUser, responseList);
	}

	private List<CommunityResponse> mapToCommunityResponses(User loginUser,
		List<CommunityBasicMapperResponse> responseList) {
		List<CommunityResponse> communityResponses = new ArrayList<>();
		responseList.forEach(response -> {
			CommunityResponse communityResponse = getVoteList(response, loginUser);
			communityResponses.add(communityResponse);
		});
		return communityResponses;
	}
}
