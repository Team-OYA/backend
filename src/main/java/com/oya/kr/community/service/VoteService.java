package com.oya.kr.community.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oya.kr.community.repository.VoteRepository;
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

	private final VoteRepository voteRepository;
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
		voteRepository.save(votedId, loginUser.getId());
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
		voteRepository.deleteByVoteCheck(votedId, loginUser.getId());
		return "투표를 취소했습니다.";
	}
}
