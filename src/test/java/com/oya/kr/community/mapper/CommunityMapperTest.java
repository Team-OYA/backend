package com.oya.kr.community.mapper;

import static org.assertj.core.api.Assertions.assertThatCode;
import static com.oya.kr.community.exception.CommunityErrorCodeList.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.domain.enums.CommunityType;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.mapper.dto.response.StatisticsResponseMapper;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;

/**
 * @author 이상민
 * @since 2024.02.18
 */
class CommunityMapperTest extends SpringApplicationTest {

	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private VoteMapper voteMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CommunityViewMapper communityViewMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private User user;
	private long communityId;
	private final String CATEGORY_CODE = "CG000001";

	@BeforeEach
	public void beforeEach(){
		user = saveUser();
	}

	@AfterEach
	public void afterEach(){
		communityMapper.deleteByUserId(user.getId());
		userMapper.deleteFromUserId(user.getId());
	}

	private User saveUser(){
		String email = "communityTest@gmail.com";
		JoinRequest joinRequest = JoinRequest.builder()
			.email(email)
			.nickname("communityTest")
			.password("password123")
			.birthDate("19900101")
			.gender(1)
			.userType(0)
			.build();
		SignupBasicMapperRequest request = new SignupBasicMapperRequest(bCryptPasswordEncoder, joinRequest);
		userMapper.insertAdminAndKakaoUser(request);
		return userMapper.findByEmail(email).orElseThrow().toDomain();
	}

	private SaveBasicMapperRequest saveCommunity(String CommunityType, List<String> votes){
		// given
		CommunityRequest communityRequest = new CommunityRequest("test","test", CATEGORY_CODE, votes);
		SaveBasicMapperRequest request = new SaveBasicMapperRequest(CommunityType, user.getId(), communityRequest);

		// when
		communityMapper.saveBasic(request);
		return request;
	}

	private void saveBasicCommunity(){
		List<String> votes = new ArrayList<>();
		CommunityRequest communityRequest = new CommunityRequest("test","test", "CG000001",votes);
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.BASIC.getName(), user.getId(), communityRequest);
		communityMapper.saveBasic(saveBasicMapperRequest);
		communityId = saveBasicMapperRequest.getPostId();
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("saveBasic() : 커뮤니티 게시글 등록할 수 있다.")
	@Test
	void saveBasic() {
		// given
		List<String> votes = new ArrayList<>();

		// when
		SaveBasicMapperRequest request = saveCommunity(CommunityType.BASIC.getName(), votes);
		CommunityBasicMapperResponse response = communityMapper.findById(request.getPostId())
			.orElseThrow(()-> new ApplicationException(NOT_EXIST_COMMUNITY));

		// then
		assertAll(
			() -> assertNotNull(response),
			() -> assertEquals(request.getPostId(), response.getId()),
			() -> assertEquals(request.getTitle(), response.getTitle()),
			() -> assertEquals(request.getDescription(), response.getDescription()),
			() -> assertEquals(request.getCategoryCode(), response.getCategoryCode())
		);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("saveVote() : 투표 게시글 등록할 수 있다.")
	@Test
	void saveVote() {
		// given
		List<String> votes = new ArrayList<>();
		votes.add("있다");
		votes.add("없다");

		// when
		SaveBasicMapperRequest request = saveCommunity(CommunityType.VOTE.getName(), votes);
		long communityId = request.getPostId();

		// then
		votes.forEach(content->
			assertThatCode(() -> communityMapper.saveVote(new SaveVoteMapperRequest(content, communityId)))
				.doesNotThrowAnyException()
		);

		// test after
		voteMapper.deleteByCommunityId(communityId);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("delete() : 커뮤니티 게시글을 삭제할 수 있다. (상태값 변경)")
	@Test
	void delete(){
		// given
		// when
		saveBasicCommunity();
		communityMapper.delete(communityId);
		boolean isDeleted = communityMapper.findById(communityId).get().isDeleted();

		// then
		assertTrue(isDeleted);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("findByAll() : 게시글 전체를 불러올 수 있다.")
	@Test
	void findByAll(){
		// given & when
		List<CommunityBasicMapperResponse> responseList =
			communityMapper.findByAll(new ReadCommunityMapperRequest(false, null, 1, 1));
		// then
		assertNotNull(responseList);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@DisplayName("조회수가 존재하지 않으면 생성하고, 아니면 수정 시간을 변경할 수 있다.")
	@Test
	void createOrUpdateCommunityView(){
		// given & when
		saveBasicCommunity();
		// then
		assertThatCode(() -> communityViewMapper.createOrUpdateCommunityView(communityId, user.getId()))
			.doesNotThrowAnyException();
		// test after
		communityViewMapper.deleteByUserId(user.getId());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@DisplayName("statistics() : 팝업 통계를 확인할 수 있다.")
	@Test
	void statistics(){
		// given & when
		int count1 = staticCount();
		saveBasicCommunity();
		int count2 = staticCount();

		// then
		assertEquals(count1+1, count2);
	}

	private int staticCount(){
		List<StatisticsResponseMapper> response = communityMapper.statistics();
		Map<String, Integer> categoryCounts = response.stream()
			.collect(Collectors.toMap(StatisticsResponseMapper::getCategoryCode, StatisticsResponseMapper::getCount));
		return categoryCounts.getOrDefault(CATEGORY_CODE, 0);
	}
}