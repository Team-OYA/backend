package com.oya.kr.commutiny.service;

import static com.oya.kr.commutiny.exception.CommunityErrorCodeList.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.commutiny.mapper.VoteMapper;
import com.oya.kr.commutiny.mapper.dto.request.VoteCheckMapperRequest;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.domain.User;

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

	/**
	 * 투표 체크 - 중복 투표?
	 *
	 * @param user, votedId
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void check(User user, long votedId) {
		VoteCheckMapperRequest request = new VoteCheckMapperRequest(user.getId(), votedId);
		int count = voteMapper.findById(request);
		if (count == 1) {
			throw new ApplicationException(VALID_VOTE);
		}
		voteMapper.checkVote(request);
	}

	/**
	 * 투표 체크 취소
	 *
	 * @param user, votedId
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public void checkDelete(User user, long votedId) {
		voteMapper.checkVoteDeleted(new VoteCheckMapperRequest(user.getId(), votedId));
	}
}
