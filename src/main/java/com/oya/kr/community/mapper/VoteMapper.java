package com.oya.kr.community.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;

public interface VoteMapper {

	int findByUserIdAndVoteId(VoteCheckMapperRequest request);
	void save(VoteCheckMapperRequest request);
	void deleteByVoteCheck(VoteCheckMapperRequest request);
	void deleteByCommunityId(long communityId);
	List<VoteResponse> findByPostId(@Param("postId") long postId);
	String findByVoteIdAndUserId(@Param("voteId") Long voteId, @Param("userId") Long userId);
	Optional<Long> findById(@Param("votedId") long votedId);
}
