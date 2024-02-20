package com.oya.kr.community.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author 이상민
 * @since 2024.02.18
 */
public interface CommunityViewMapper {

	void createOrUpdateCommunityView(@Param("communityId") Long communityId, @Param("userId") Long userId);
	void deleteByUserId(long userId);
}
