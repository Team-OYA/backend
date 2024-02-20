package com.oya.kr.community.mapper;

import java.util.List;

import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;
import com.oya.kr.community.mapper.dto.request.ReadCollectionsMapperRequest;
import com.oya.kr.community.mapper.dto.response.CommunityBasicMapperResponse;

public interface CollectionMapper {

	int checkCollection(CollectionMapperRequest collectionMapperRequest);
	void saveCollection(CollectionMapperRequest collectionMapperRequest);
	boolean isDeleted(CollectionMapperRequest collectionMapperRequest);
	void changeDelete(CollectionMapperRequest collectionMapperRequest);
	List<CommunityBasicMapperResponse> findByCollections(ReadCollectionsMapperRequest readCollectionsMapperRequest);
	void deleteFromUserId(CollectionMapperRequest collectionMapperRequest);
}
