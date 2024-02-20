package com.oya.kr.community.mapper;

import org.apache.ibatis.annotations.Param;

public interface CommunityViewMapper {

	void createOrUpdateCommunityView(@Param("communityId") Long communityId, @Param("userId") Long userId);
	void deleteByUserId(long userId);
}
