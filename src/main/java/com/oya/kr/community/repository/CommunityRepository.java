package com.oya.kr.community.repository;

import static com.oya.kr.community.exception.CommunityErrorCodeList.FAIL_COMMUNITY_COLLECTION;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.community.controller.dto.request.CommunityRequest;
import com.oya.kr.community.domain.enums.CommunityType;
import com.oya.kr.community.exception.CommunityErrorCodeList;
import com.oya.kr.community.mapper.CollectionMapper;
import com.oya.kr.community.mapper.CommunityMapper;
import com.oya.kr.community.mapper.CommunityViewMapper;
import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;
import com.oya.kr.community.mapper.dto.request.ReadCollectionsMapperRequest;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.mapper.dto.response.StatisticsResponseMapper;
import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommunityRepository {

    private final CommunityMapper communityMapper;
    private final CommunityViewMapper communityViewMapper;
    private final CollectionMapper collectionMapper;

    /**
     * 게시글 기본 정보 조회
     *
     * @parameter Long
     * @return CommunityBasicMapperResponse
     * @author 이상민
     * @since 2024.02.18
     */
    public CommunityBasicMapperResponse findById(Long id) {
        CommunityBasicMapperResponse response = communityMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(CommunityErrorCodeList.NOT_EXIST_COMMUNITY));
        if (response.isDeleted()) {
            throw new ApplicationException(CommunityErrorCodeList.DELETED_COMMUNITY);
        }
        return response;
    }

    /**
     * 게시글 기본 정보 조회
     *
     * @parameter Long, Long
     * @return CommunityBasicMapperResponse
     * @author 이상민
     * @since 2024.02.18
     */
    public CommunityBasicMapperResponse findByIdWithView(Long id, Long userId) {
        CommunityBasicMapperResponse response = communityMapper.findById(id)
            .orElseThrow(() -> new ApplicationException(CommunityErrorCodeList.NOT_EXIST_COMMUNITY));
        communityViewMapper.createOrUpdateCommunityView(id, userId); // 조회수 증가
        if (response.isDeleted()) {
            throw new ApplicationException(CommunityErrorCodeList.DELETED_COMMUNITY);
        }
        return response;
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long
     * @return List<String>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<String> findImageById(Long id) {
        return communityMapper.findByImage(id);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter String, int, int
     * @return List<CommunityBasicMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<CommunityBasicMapperResponse> findAll(String userType, int pageNo, int amount) {
        return communityMapper.findByAll(new ReadCommunityMapperRequest(false, userType, pageNo, amount));
    }

    /**
     * 게시글 type 에 따른 호출
     *
     * @parameter Long, int, int
     * @return List<CommunityBasicMapperResponse>
     * @author 이상민
     * @since 2024.02.22
     */
    public List<CommunityBasicMapperResponse> findByType(String userType, int pageNo, int amount) {
        return communityMapper.findByType(new ReadCommunityMapperRequest(false, userType, pageNo, amount));
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, int, int
     * @return List<CommunityBasicMapperResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<CommunityBasicMapperResponse> findAllCollections(Long userId, int pageNo, int amount) {
        return collectionMapper.findByAll(new ReadCollectionsMapperRequest(userId,false,false, "All", pageNo, amount));
    }

    /**
     * repository 계층으로 분리
     *
     * @return List<StatisticsResponseMapper>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<StatisticsResponseMapper> statistics() {
        return communityMapper.statistics();
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter CommunityType, User, CommunityRequest, List<String>
     * @return long
     * @author 김유빈
     * @since 2024.02.21
     */
    public long saveForBasic(CommunityType communityType, User user, CommunityRequest communityRequest, List<String> images) {
        SaveBasicMapperRequest request = new SaveBasicMapperRequest(communityType.getName(), user.getId(), communityRequest);
        communityMapper.saveBasic(request);
        images.forEach(image -> communityMapper.saveImage(image, request.getPostId()));
        return request.getPostId();
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, List<String>
     * @author 김유빈
     * @since 2024.02.21
     */
    public void saveAllForVote(Long communityId, List<String> votes) {
        votes.forEach(content ->
            communityMapper.saveVote(new SaveVoteMapperRequest(content, communityId))
        );
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter CollectionMapperRequest
     * @return boolean
     * @author 김유빈
     * @since 2024.02.21
     */
    public boolean saveCollections(CollectionMapperRequest request) {
        int checkCollection = collectionMapper.check(request);
        if(checkCollection == 0){
            collectionMapper.save(request);
            return true;
        }else if(checkCollection == 1){
            boolean isDeleted = collectionMapper.isDeleted(request);
            request.updateIsDeleted(isDeleted);
            if(!isDeleted){
                collectionMapper.delete(request);
                return false;
            }else{
                collectionMapper.delete(request);
                return true;
            }
        }else{
            throw new ApplicationException(FAIL_COMMUNITY_COLLECTION);
        }
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Long
     * @author 김유빈
     * @since 2024.02.21
     */
    public void delete(Long id, Long userId) {
        CommunityBasicMapperResponse response = findById(id);
        if (userId != response.getWriteId()) {
            throw new ApplicationException(CommunityErrorCodeList.INVALID_COMMUNITY);
        }
        communityMapper.delete(id);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Long
     * @author 김유빈
     * @since 2024.02.21
     */
    public int findSizeByType(User loginUser, String type) {
        if(type.equals("all")){
            return communityMapper.findSizeByAll();
        }else if(type.equals("collections")){
            return communityMapper.findSizeByCollection(loginUser.getId());
        }else{
            return communityMapper.findSizeByType(type);
        }
	}

    /**
     * 커뮤니티 게시글 스크랩 여부 반환
     *
     * @parameter long, Long
     * @author 김유빈
     * @since 2024.02.26
     */
    public boolean existCollection(long communityId, Long userId) {
        return communityMapper.existCollection(communityId, userId);
    }
}
