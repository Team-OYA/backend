package com.oya.kr.community.mapper;

import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;

public interface VoteMapper {

	int findById(VoteCheckMapperRequest request);

	void checkVote(VoteCheckMapperRequest request);

	void checkVoteDeleted(VoteCheckMapperRequest request);
}
