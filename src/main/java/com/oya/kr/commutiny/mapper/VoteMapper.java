package com.oya.kr.commutiny.mapper;

import com.oya.kr.commutiny.mapper.dto.request.VoteCheckMapperRequest;

public interface VoteMapper {

	int findById(VoteCheckMapperRequest request);

	void checkVote(VoteCheckMapperRequest request);

	void checkVoteDeleted(VoteCheckMapperRequest request);
}
