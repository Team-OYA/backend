package com.oya.kr.chat.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.chat.mapper.NotificationMapper;
import com.oya.kr.chat.mapper.dto.request.NotificationMapperRequest;
import com.oya.kr.chat.mapper.dto.response.NotificationListMapperResponse;
import com.oya.kr.chat.mapper.dto.response.NotificationMapperResponse;
import com.oya.kr.user.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.03.07
 */
@RequiredArgsConstructor
@Repository
public class NotificationRepository {

	private final NotificationMapper notificationMapper;

	/**
	 * fcm 토큰 저장
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public void save(NotificationMapperRequest notification) {
		notificationMapper.save(notification);
	}

	/**
	 * 유저 찾기
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public NotificationMapperResponse findByUser(User user) {
		return notificationMapper.findByUser(user.getId());
	}

	/**
	 * token update
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public void update(NotificationMapperRequest notification) {
		notificationMapper.update(notification);
	}

	/**
	 * 전체 알람 찾기
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public List<NotificationListMapperResponse> findAll() {
		return notificationMapper.findAll();
	}

	/**
	 * userType 이 admin 인 알람 token 찾기
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public List<NotificationListMapperResponse> findByAdmin() {
		return notificationMapper.findByUserType();
	}
}
