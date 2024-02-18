package com.oya.kr.commutiny.mapper;

import static com.oya.kr.community.exception.CommunityErrorCodeList.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.mapper.CommunityMapper;
import com.oya.kr.community.domain.CommunityType;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.global.exception.ApplicationException;

/**
 * @author 이상민
 * @since 2024.02.18
 */
class CommunityMapperTest extends SpringApplicationTest {

	@Autowired
	private CommunityMapper communityMapper;

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("커뮤니티 게시글 등록하기")
	@Test
	void saveBasic() {
		// given
		List<String> votes = new ArrayList<>();
		// when
		SaveBasicMapperRequest request = save(CommunityType.BASIC.getName(), votes);
		// then
		CommunityBasicMapperResponse response = communityMapper.getCommunityById(request.getPostId())
			.orElseThrow(()-> new ApplicationException(NOT_EXIST_COMMUNITY));
		assertAll(
			() -> assertNotNull(response),
			() -> assertEquals(request.getPostId(), response.getId()),
			() -> assertEquals(request.getTitle(), response.getTitle()),
			() -> assertEquals(request.getDescription(), response.getDescription()),
			() -> assertEquals(request.getPopupid(), response.getPopupId())
		);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("투표 등록하기")
	@Test
	void saveVote() {
		// given
		List<String> votes = new ArrayList<>();
		votes.add("있다");
		votes.add("없다");

		// when
		SaveBasicMapperRequest request = save(CommunityType.VOTE.getName(), votes);
		long postId = request.getPostId();

		// then
		votes.forEach(content->
			communityMapper.saveVote(new SaveVoteMapperRequest(content, postId))
		);

	}

	private SaveBasicMapperRequest save(String CommunityType, List<String> votes){
		// given
		long userId = 1;
		CommunityRequest communityRequest = new CommunityRequest("test","test", "CG000001",votes);
		SaveBasicMapperRequest request = new SaveBasicMapperRequest(CommunityType, userId, communityRequest);
		// when
		communityMapper.saveBasic(request);
		return request;
	}


	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("투표정보를 불러올 수 있다.")
	@Test
	void getVoteResponse(){
		// given
		long communityId = 5;
		long userId = 1;

		// when
		List<VoteResponse> list = communityMapper.getVoteInfo(communityId);
		list.forEach(vote->{
			boolean check = Boolean.parseBoolean(communityMapper.checkUserVote(vote.getVote_id(), userId));
			vote.setChecked(check);
		});
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("게시글을 삭제할 수 있다.")
	@Test
	void delete(){
		// given
		long communityId = 5;

		// when & then
		communityMapper.delete(communityId);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("게시글 전체를 불러올 수 있다.")
	@Test
	void findByAll(){
		// given
		long communityId = 5;

		// when & then
		List<CommunityBasicMapperResponse> responseList = communityMapper.findByAll(new ReadCommunityMapperRequest(false, null, 1, 1));

	}
}