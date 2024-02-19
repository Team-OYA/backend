package com.oya.kr.community.service;

import static com.oya.kr.user.exception.UserErrorCodeList.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.community.exception.CommunityErrorCodeList;
import com.oya.kr.community.mapper.VoteMapper;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.18
 */
@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {

	private final VoteMapper voteMapper;
	private final UserMapper userMapper;

	/**
	 * 투표 체크
	 *
	 * @param email, votedId
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String check(String email, long votedId) {
		User loginUser = findByEmail(email);
		VoteCheckMapperRequest request = new VoteCheckMapperRequest(loginUser.getId(), votedId);
		int count = voteMapper.findById(request);
		if (count >= 1) {
			throw new ApplicationException(CommunityErrorCodeList.VALID_VOTE);
		}
		voteMapper.checkVote(request);
		return "투표를 체크했습니다.";
	}

	/**
	 * 투표 체크 취소
	 *
	 * @param email, votedId
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String checkDelete(String email, long votedId) {
		User loginUser = findByEmail(email);
		voteMapper.checkVoteDeleted(new VoteCheckMapperRequest(loginUser.getId(), votedId));
		return "투표를 취소했습니다.";
	}

	private User findByEmail(String email) {
		UserMapperResponse userMapperResponse = userMapper.findByEmail(email)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER));
		return userMapperResponse.toDomain();
	}
}
