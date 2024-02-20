package com.oya.kr.community.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.domain.CommunityType;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;

/**
 * @author 이상민
 * @since 2024.02.18
 */
class VoteMapperTest extends SpringApplicationTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private VoteMapper voteMapper;

	private User user;
	private VoteCheckMapperRequest voteCheckMapperRequest;
	private long communityId;

	@BeforeEach
	public void beforeEach(){
		user = saveUser();
		voteCheckMapperRequest = getVoteCheckMapperRequest();
	}

	@AfterEach
	public void afterEach(){
		voteMapper.deleteByVoteCheck(voteCheckMapperRequest);
		voteMapper.deleteByCommunityId(communityId);
		communityMapper.deleteByUserId(user.getId());
		userMapper.deleteFromUserId(user.getId());
	}

	private User saveUser(){
		String email = "voteTest@gmail.com";
		JoinRequest joinRequest = JoinRequest.builder()
			.email(email)
			.nickname("voteTest")
			.password("password123")
			.birthDate("19900101")
			.gender(1)
			.userType(0)
			.build();
		SignupBasicMapperRequest request = new SignupBasicMapperRequest(bCryptPasswordEncoder, joinRequest);
		userMapper.insertAdminAndKakaoUser(request);
		return userMapper.findByEmail(email).orElseThrow().toDomain();
	}

	private VoteCheckMapperRequest getVoteCheckMapperRequest(){
		List<String> votes = new ArrayList<>();
		votes.add("있다");
		votes.add("없다");
		CommunityRequest communityRequest = new CommunityRequest("test","test", "CG000001",votes);
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.VOTE.getName(), user.getId(), communityRequest);
		communityMapper.saveBasic(saveBasicMapperRequest);
		communityId = saveBasicMapperRequest.getPostId();
		votes.forEach(content->
			communityMapper.saveVote(new SaveVoteMapperRequest(content, communityId))
		);
		List<VoteResponse> list = voteMapper.findByPostId(communityId);
		return new VoteCheckMapperRequest(user.getId(), list.get(0).getVote_id());
	}

	@DisplayName("checkVote() : 투표할 수 있다.")
	@Test
	void checkVote(){
		// given
		// when
		// then
		assertThatCode(() -> voteMapper.save(voteCheckMapperRequest))
			.doesNotThrowAnyException();
	}

	@DisplayName("checkVoteDeleted() : 투표를 취소할 수 있다.")
	@Test
	void checkVoteDeleted(){
		// given
		// when
		// then
		assertThatCode(() -> voteMapper.deleteByVoteCheck(voteCheckMapperRequest))
			.doesNotThrowAnyException();
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("getVoteResponse() : 투표정보를 불러올 수 있다.")
	@Test
	void getVoteResponse(){
		// given
		List<VoteResponse> list = voteMapper.findByPostId(communityId);
		// then
		assertThat(list).hasSize(2);
		// test after
		voteMapper.deleteByCommunityId(communityId);
	}
}