package com.oya.kr.community.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

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
import com.oya.kr.community.domain.enums.CommunityType;
import com.oya.kr.community.mapper.dto.request.ReadCollectionsMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;

/**
 * @author 이상민
 * @since 2024.02.20
 */
class CollectionMapperTest extends SpringApplicationTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private CollectionMapper collectionMapper;

	private User user;
	private CollectionMapperRequest collectionMapperRequest;

	@BeforeEach
	public void beforeEach(){
		user = saveUser();
		collectionMapperRequest = getCollectionMapperRequest(user);
	}

	@AfterEach
	public void afterEach(){
		collectionMapper.deleteByUserId(collectionMapperRequest);
		communityMapper.deleteByUserId(collectionMapperRequest.getUserId());
		userMapper.deleteFromUserId(collectionMapperRequest.getUserId());
	}

	private User saveUser(){
		String email = "test1234@gmail.com";
		JoinRequest joinRequest = JoinRequest.builder()
			.email(email)
			.nickname("user2")
			.password("password123")
			.birthDate("19900101")
			.gender(1)
			.userType(2)
			.build();
		SignupBasicMapperRequest request = new SignupBasicMapperRequest(bCryptPasswordEncoder, joinRequest);
		userMapper.insertAdminAndKakaoUser(request);
		return userMapper.findByEmail(email).orElseThrow().toDomain();
	}

	private CollectionMapperRequest getCollectionMapperRequest(User user){
		List<String> votes = new ArrayList<>();
		CommunityRequest communityRequest = new CommunityRequest("test","test", "CG000001",votes);
		SaveBasicMapperRequest saveBasicMapperRequest = new SaveBasicMapperRequest(CommunityType.BASIC.getName(), user.getId(), communityRequest);
		communityMapper.saveBasic(saveBasicMapperRequest);
		long communityId = saveBasicMapperRequest.getPostId();
		return new CollectionMapperRequest(communityId, user.getId());
	}

	/**
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@DisplayName("saveCollection() : 스크랩을 할 수 있다.")
	@Test
	void saveCollection(){
		// given
		// when & then
		assertThatCode(() -> collectionMapper.save(collectionMapperRequest))
			.doesNotThrowAnyException();
	}

	/**
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@DisplayName("checkCollection() : 스크랩 유무를 확인할 수 있다.")
	@Test
	public void checkCollection(){
		// given
		// when
		collectionMapper.save(collectionMapperRequest);
		int result = collectionMapper.check(collectionMapperRequest);

		// then
		assertEquals(1, result);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@DisplayName("isDeleted() : 삭제 유무를 확인할 수 있다.")
	@Test
	void isDeleted(){
		// given
		// when
		collectionMapper.save(collectionMapperRequest);
		boolean isDeleted = collectionMapper.isDeleted(collectionMapperRequest);

		// then
		assertFalse(isDeleted);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@DisplayName("changeDelete() : DELETED 코드의 상태값을 수정할 수 있다.")
	@Test
	void changeDelete(){
		// given
		// when
		collectionMapper.save(collectionMapperRequest);
		collectionMapperRequest.updateIsDeleted(collectionMapper.isDeleted(collectionMapperRequest));
		collectionMapper.delete(collectionMapperRequest);
		boolean isDeleted = collectionMapper.isDeleted(collectionMapperRequest);

		// then
		assertTrue(isDeleted);
	}

	/**
	 * @author 이상민
	 * @since 2024.02.20
	 */
	@DisplayName("findByCollections() : 스크랩한 모든 커뮤니티 게시글을 확인할 수 있다.")
	@Test
	void findByCollections(){
		// given
		// when
		collectionMapper.save(collectionMapperRequest);
		ReadCollectionsMapperRequest request = new ReadCollectionsMapperRequest(user.getId(),false,false, null, 0, 5);
		List<CommunityBasicMapperResponse> responses = collectionMapper.findByAll(request);
		// then
		assertNotNull(responses);
	}
}