package com.oya.kr.commutiny.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.community.mapper.VoteMapper;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;

/**
 * @author 이상민
 * @since 2024.02.18
 */
class VoteMapperTest extends SpringApplicationTest {

	@Autowired
	private VoteMapper voteMapper;

	@DisplayName("투표할 수 있다.")
	@Test
	void checkVote(){
		// given
		VoteCheckMapperRequest request = new VoteCheckMapperRequest(2, 6);
		// when
		voteMapper.checkVote(request);
	}

	@DisplayName("투표를 취소할 수 있다.")
	@Test
	void checkVoteDeleted(){
		// given
		VoteCheckMapperRequest request = new VoteCheckMapperRequest(2, 6);
		// when
		voteMapper.checkVoteDeleted(request);
	}

}