package com.oya.kr.community.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;

public interface CommunityMapper {

	void saveBasic(SaveBasicMapperRequest saveBasicMapperRequest);

	Optional<CommunityBasicMapperResponse> getCommunityById(long communityId);

	void saveVote(SaveVoteMapperRequest saveVoteMapperRequest);

	List<VoteResponse> getVoteInfo(@Param("postId") long postId);

	String checkUserVote(@Param("voteId") Long voteId, @Param("userId") Long userId);

	void delete(long communityId);

	List<CommunityBasicMapperResponse> findByAll(ReadCommunityMapperRequest readCommunityMapperRequest);

	List<CommunityBasicMapperResponse> findByType(ReadCommunityMapperRequest readCommunityMapperRequest);
}
