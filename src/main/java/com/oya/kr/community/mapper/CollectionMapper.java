package com.oya.kr.community.mapper;

import com.oya.kr.community.mapper.dto.request.CollectionMapperRequest;

public interface CollectionMapper {
	int checkCollection(CollectionMapperRequest collectionMapperRequest);
	void saveCollection(CollectionMapperRequest collectionMapperRequest);
	boolean isDeleted(CollectionMapperRequest collectionMapperRequest);
	void changeDelete(CollectionMapperRequest collectionMapperRequest);
	void deleteFromUserId(CollectionMapperRequest collectionMapperRequest);
}
