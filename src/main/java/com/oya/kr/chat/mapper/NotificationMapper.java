package com.oya.kr.chat.mapper;

import java.util.List;

import com.oya.kr.chat.mapper.dto.request.NotificationMapperRequest;
import com.oya.kr.chat.mapper.dto.response.NotificationListMapperResponse;
import com.oya.kr.chat.mapper.dto.response.NotificationMapperResponse;

/**
 * @author 이상민
 * @since 2024.03.07
 */
public interface NotificationMapper {
	void save(NotificationMapperRequest notification);

	NotificationMapperResponse findByUser(Long id);

	void update(NotificationMapperRequest notification);

	List<NotificationListMapperResponse> findAll();

	List<NotificationListMapperResponse> findByUserType();
}
