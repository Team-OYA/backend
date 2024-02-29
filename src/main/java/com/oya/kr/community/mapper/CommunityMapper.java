package com.oya.kr.community.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.community.mapper.dto.request.ReadMeMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveBasicMapperRequest;
import com.oya.kr.community.mapper.dto.request.SaveVoteMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;
import com.oya.kr.community.mapper.dto.request.ReadCommunityMapperRequest;
import com.oya.kr.community.mapper.dto.response.StatisticsResponseMapper;
import com.oya.kr.popup.mapper.dto.response.StatisticsCommunityMapperResponse;

/**
 * @author 이상민
 * @since 2024.02.17
 */
public interface CommunityMapper {

	void saveBasic(SaveBasicMapperRequest saveBasicMapperRequest);
	void saveVote(SaveVoteMapperRequest saveVoteMapperRequest);
	Optional<CommunityBasicMapperResponse> findById(long communityId);
	List<CommunityBasicMapperResponse> findByAll(ReadCommunityMapperRequest readCommunityMapperRequest);
	List<CommunityBasicMapperResponse> findByType(ReadCommunityMapperRequest readCommunityMapperRequest);
	void delete(long communityId);
	void deleteByUserId(Long userId);
	void saveImage(@Param("imageUrl") String imageUrl, @Param("communityId") long communityId);
	List<String> findByImage(long communityId);
	List<StatisticsResponseMapper> statistics();
	StatisticsCommunityMapperResponse statisticsForBusiness(Long userId);
	int findSizeByAll();
	int findSizeByType(String type);
	int findSizeByCollection(Long userId);
	boolean existCollection(@Param("communityId") long communityId, @Param("userId") Long userId);
	List<CommunityBasicMapperResponse> findAllMe(ReadMeMapperRequest readMeMapperRequest);
	int findByWriteId(Long userId);
}
