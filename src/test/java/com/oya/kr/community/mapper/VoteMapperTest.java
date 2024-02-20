package com.oya.kr.community.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.domain.CommunityType;
import com.oya.kr.community.mapper.CommunityMapper;
import com.oya.kr.community.mapper.VoteMapper;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;

/**
 * @author 이상민
 * @since 2024.02.18
 */
class VoteMapperTest extends SpringApplicationTest {

	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private VoteMapper voteMapper;

	@DisplayName("투표할 수 있다.")
	@Test
	void checkVote(){
		// given & when
		VoteCheckMapperRequest request = request();

		// then
		assertThatCode(() -> voteMapper.checkVote(request))
			.doesNotThrowAnyException();
	}

	@DisplayName("투표를 취소할 수 있다.")
	@Test
	void checkVoteDeleted(){
		// given & when
		VoteCheckMapperRequest request = request();

		// then
		assertThatCode(() -> voteMapper.checkVoteDeleted(request))
			.doesNotThrowAnyException();
	}

	private VoteCheckMapperRequest request(){
		// given
		long userId = 1;
		List<String> votes = new ArrayList<>();
		votes.add("있다");
		votes.add("없다");
		CommunityRequest communityRequest = new CommunityRequest("test","test", "CG000001",votes);
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.VOTE.getName(), userId, communityRequest);
		communityMapper.saveBasic(saveBasicMapperRequest);
		long communityId = saveBasicMapperRequest.getPostId();
		votes.forEach(content->
			communityMapper.saveVote(new SaveVoteMapperRequest(content, communityId))
		);

		// when
		List<VoteResponse> list = communityMapper.getVoteInfo(communityId);
		return new VoteCheckMapperRequest(1, list.get(0).getVote_id());
	}
}