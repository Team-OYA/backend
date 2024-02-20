package com.oya.kr.community.mapper;

import java.util.List;

import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;
import com.oya.kr.community.mapper.dto.request.ReadCollectionsMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;

public interface CollectionMapper {

	void save(CollectionMapperRequest collectionMapperRequest);
	int check(CollectionMapperRequest collectionMapperRequest);
	boolean isDeleted(CollectionMapperRequest collectionMapperRequest);
	void delete(CollectionMapperRequest collectionMapperRequest);
	List<CommunityBasicMapperResponse> findByAll(ReadCollectionsMapperRequest readCollectionsMapperRequest);
	void deleteByUserId(CollectionMapperRequest collectionMapperRequest);
}
