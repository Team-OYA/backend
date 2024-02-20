package com.oya.kr.community.service;

import static com.oya.kr.community.exception.CommunityErrorCodeList.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.community.exception.CommunityErrorCodeList;
import com.oya.kr.community.mapper.VoteMapper;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

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
	private final UserRepository userRepository;

	/**
	 * 투표 체크
	 *
	 * @param email, votedId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String check(String email, long votedId) {
		User loginUser = userRepository.findByEmail(email);
		voteMapper.findById(votedId).orElseThrow(() -> new ApplicationException(NOT_EXIST_VOTE));
		VoteCheckMapperRequest request = new VoteCheckMapperRequest(loginUser.getId(), votedId);
		int count = voteMapper.findByUserIdAndVoteId(request);
		if (count >= 1) {
			throw new ApplicationException(CommunityErrorCodeList.VALID_VOTE);
		}
		voteMapper.save(request);
		return "투표를 체크했습니다.";
	}

	/**
	 * 투표 체크 취소
	 *
	 * @param email, votedId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String checkDelete(String email, long votedId) {
		User loginUser = userRepository.findByEmail(email);
		voteMapper.deleteByVoteCheck(new VoteCheckMapperRequest(loginUser.getId(), votedId));
		return "투표를 취소했습니다.";
	}
}
