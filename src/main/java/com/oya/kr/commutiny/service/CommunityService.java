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
import com.oya.kr.commutiny.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.commutiny.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.commutiny.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.domain.User;
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
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.BASIC.getName(), user.getId(), communityRequest);
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
		SaveBasicMapperRequest request = new SaveBasicMapperRequest(CommunityType.VOTE.getName(), user.getId(), communityRequest);
		communityMapper.saveBasic(request);
		long postId = request.getPostId();
		communityRequest.getVotes().forEach(content->
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

		User user = getUserFromResponse(response);
		Community community = response.toDomain(user);

		List<VoteResponse> voteResponseList = new ArrayList<>();
		if (community.getCommunityType().equals(CommunityType.VOTE)) {
			voteResponseList = addVoteResponse(loginUser, communityId);
		}

		return CommunityResponse.from(community, voteResponseList);
	}

	private CommunityBasicMapperResponse getCommunityResponseOrThrow(long communityId) {
		return communityMapper.getCommunityById(communityId)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_COMMUNITY));
	}

	private User getUserFromResponse(CommunityBasicMapperResponse response) {
		return userMapper.findById(response.getWriteId())
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
			.toDomain();
	}

	private List<VoteResponse> addVoteResponse(User loginUser, long communityId) {
		List<VoteResponse> voteResponseList = communityMapper.getVoteInfo(communityId);

		voteResponseList.forEach(VoteResponse->{
			boolean check = Boolean.parseBoolean(communityMapper.checkUserVote(VoteResponse.getVote_id(), loginUser.getId()));
			VoteResponse.setChecked(check);
		});

		return voteResponseList;
	}

	/**
	 * 커뮤니티 게시글 삭제
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
}
