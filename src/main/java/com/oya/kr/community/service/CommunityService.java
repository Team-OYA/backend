package com.oya.kr.community.service;

import static com.oya.kr.community.exception.CommunityErrorCodeList.*;
import static com.oya.kr.user.exception.UserErrorCodeList.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.controller.dto.response.CommunityDetailResponse;
import com.oya.kr.community.controller.dto.response.CommunityResponse;
import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.domain.enums.CommunityType;
import com.oya.kr.community.exception.CommunityErrorCodeList;
import com.oya.kr.community.mapper.CommunityMapper;
import com.oya.kr.community.mapper.CollectionMapper;
import com.oya.kr.community.mapper.CommunityViewMapper;
import com.oya.kr.community.mapper.VoteMapper;
import com.oya.kr.community.mapper.dto.request.ReadCollectionsMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.domain.Community;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.global.dto.Pagination;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.17
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Service
@Transactional
public class CommunityService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final CommunityMapper communityMapper;
	private final UserMapper userMapper;
	private final VoteMapper voteMapper;
	private final CommunityViewMapper communityViewMapper;
	private final CollectionMapper collectionMapper;

	private final StorageConnector s3Connector;

	/**
	 * 커뮤니티 게시글(일반, 투표) 등록
	 *
	 * @param email,  communityRequest
	 * @param images
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String save(String communityType, String email, CommunityRequest communityRequest, List<MultipartFile> images) {
		User loginUser = findByEmail(email);
		SaveBasicMapperRequest request = new SaveBasicMapperRequest(CommunityType.from(communityType).getName(), loginUser.getId(), communityRequest);
		communityMapper.saveBasic(request);
		saveCommunityImages(request.getPostId(), images);

		if(communityType.equals("vote")) {
			long communityId = request.getPostId();
			communityRequest.getVotes().forEach(content ->
				communityMapper.saveVote(new SaveVoteMapperRequest(content, communityId))
			);
		}
		return "게시글이 등록되었습니다.";
	}

	/**
	 * 이미지 저장
	 *
	 * @param communityId,  images
	 * @author 이상민
	 * @since 2024.02.19
	 */
	private void saveCommunityImages(long communityId, List<MultipartFile> images){
		for (MultipartFile image : images) {
			String imageUrl = s3Connector.save(image);
			communityMapper.saveImage(imageUrl, communityId);
		}
	}

	/**
	 * 커뮤니티 게시글 상세 조회
	 *
	 * @param email, communityId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public CommunityDetailResponse read(String email, long communityId) {
		User loginUser = findByEmail(email);
		communityViewMapper.createOrUpdateCommunityView(communityId, loginUser.getId()); // 조회수 증가
		CommunityBasicMapperResponse response = getCommunityResponseOrThrow(communityId);

		User writeUser = findByUserId(response.getWriteId());
		List<String> imageList = communityMapper.findByImage(response.getId());
		Community community = response.toDomain(writeUser);
		int countView = response.getCountView();

		List<VoteResponse> voteResponseList = getVoteList(community.getCommunityType().getName(), loginUser, community.getId());
		return CommunityDetailResponse.from(imageList, community, countView, voteResponseList);
	}

	/**
	 * 게시글 기본 정보 조회
	 *
	 * @param communityId
	 * @return CommunityBasicMapperResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private CommunityBasicMapperResponse getCommunityResponseOrThrow(long communityId) {
		CommunityBasicMapperResponse response = communityMapper.findById(communityId)
			.orElseThrow(() -> new ApplicationException(CommunityErrorCodeList.NOT_EXIST_COMMUNITY));
		if (response.isDeleted()) {
			throw new ApplicationException(CommunityErrorCodeList.DELETED_COMMUNITY);
		}
		return response;
	}

	/**
	 * 투표 정보 조회
	 *
	 * @param type, loginUser, communityId
	 * @return CommunityResponse
	 * @author 이상민
	 * @since 2024.02.18
	 */
	private List<VoteResponse> getVoteList(String type, User loginUser, long communityId) {
		if (type.equals(CommunityType.VOTE.getName())) {
			List<VoteResponse> voteResponseList = voteMapper.findByPostId(communityId);
			voteResponseList.forEach(VoteResponse -> {
				boolean check = Boolean.parseBoolean(voteMapper.findByVoteIdAndUserId(VoteResponse.getVote_id(), loginUser.getId()));
				VoteResponse.setChecked(check);
			});
			return voteResponseList;
		}
		return null;
	}

	/**
	 * 커뮤니티 게시글 삭제
	 *
	 * @param email, communityId
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	public String delete(String email, long communityId) {
		User loginUser = findByEmail(email);
		CommunityBasicMapperResponse response = getCommunityResponseOrThrow(communityId);

		if (loginUser.getId() != response.getWriteId()) {
			throw new ApplicationException(CommunityErrorCodeList.INVALID_COMMUNITY);
		}
		communityMapper.delete(communityId);
		return "게시글이 삭제되었습니다.";
	}

	/**
	 * 커뮤니티 게시글 리스트 조회
	 *
	 * @param email
	 * @param pagination
	 * @return String
	 * @author 이상민
	 * @since 2024.02.18
	 */
	@Transactional(readOnly = true)
	public CommunityResponse reads(String type,String email, Pagination pagination) {
		User loginUser = findByEmail(email);
		List<CommunityBasicMapperResponse> responseList;
		if(type.equals("all")){
			responseList = communityMapper.findByAll(new ReadCommunityMapperRequest(false, null, pagination.getPageNo(), pagination.getAmount()));
		}else if(type.equals("collections")){
			responseList = collectionMapper.findByAll(new ReadCollectionsMapperRequest(loginUser.getId(),false,false, null, pagination.getPageNo(), pagination.getAmount()));
		}else{
			String communityType = CommunityType.from(type).getName();
			responseList = communityMapper.findByAll(new ReadCommunityMapperRequest(false, communityType, pagination.getPageNo(), pagination.getAmount()));
		}
		return mapToCommunityResponses(loginUser, responseList);
	}

	private CommunityResponse mapToCommunityResponses(User loginUser, List<CommunityBasicMapperResponse> responseList) {
		List<CommunityDetailResponse> communityDetailRespons = new ArrayList<>();
		responseList.forEach(response -> {
			CommunityDetailResponse communityDetailResponse = read(loginUser.getEmail(), response.getId());
			communityDetailRespons.add(communityDetailResponse);
		});
		return new CommunityResponse(communityDetailRespons);
	}

	private User findByUserId(long userId) {
		return userMapper.findById(userId)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
			.toDomain();
	}

	private User findByEmail(String email) {
		UserMapperResponse userMapperResponse = userMapper.findByEmail(email)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST_USER));
		return userMapperResponse.toDomain();
	}

	/**
	 * 커뮤니티 게시글 스크랩
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.02.20
	 */
	public String saveCollection(String email, long communityId) {
		User loginUser = findByEmail(email);
		CollectionMapperRequest request = new CollectionMapperRequest(communityId, loginUser.getId());
		int checkCollection = collectionMapper.check(request);
		if(checkCollection == 0){
			collectionMapper.save(request);
			return "스크랩 완료되었습니다.";
		}else if(checkCollection == 1){
			boolean isDeleted = collectionMapper.isDeleted(request);
			request.updateIsDeleted(isDeleted);
			if(!isDeleted){
				collectionMapper.delete(request);
				return "스크랩 취소되었습니다.";
			}else{
				collectionMapper.delete(request);
				return "스크랩 완료되었습니다.";
			}
		}else{
			throw new ApplicationException(FAIL_COMMUNITY_COLLECTION);
		}
	}
}
