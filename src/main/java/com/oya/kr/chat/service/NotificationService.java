package com.oya.kr.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.oya.kr.chat.controller.dto.NotificationRequestDto;
import com.oya.kr.chat.mapper.dto.request.NotificationMapperRequest;
import com.oya.kr.chat.mapper.dto.response.NotificationListMapperResponse;
import com.oya.kr.chat.mapper.dto.response.NotificationMapperResponse;
import com.oya.kr.chat.repository.ChatRoomRepository;
import com.oya.kr.chat.repository.NotificationRepository;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 이상민
 * @since 2024.03.07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;

	/**
	 * fcm 토큰 저장
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	@Transactional
	public void saveNotification(String email, String token) {
		User user = userRepository.findByEmail(email);
		NotificationMapperResponse existCheck = notificationRepository.findByUser(user);
		NotificationMapperRequest notification = new NotificationMapperRequest(user.getId(), token);
		if(existCheck == null){
			notificationRepository.save(notification);
		}else{
			notificationRepository.update(notification);
		}
	}

	/**
	 * 알람 전송
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public void sendNotification(Long sendUserId, Long roomId) {
		User user = userRepository.findByUserId(sendUserId);
		if (user.getUserType().isBusiness()) {
			sendNotificationToAdmins();
		} else {
			sendNotificationToRoomOwner(roomId);
		}
	}

	/**
	 * 관리자에게 알람 전송
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	private void sendNotificationToAdmins() {
		List<NotificationListMapperResponse> adminList = notificationRepository.findByAdmin();
		for (NotificationListMapperResponse admin : adminList) {
			NotificationRequestDto notificationRequestDto =
				new NotificationRequestDto("알람입니다.", "메시지가 도착했습니다.", getNotificationToken(admin.getUserId()));
			sendNotification(notificationRequestDto);
		}
		log.info("보내졌습니다! (admin)");
	}

	/**
	 * 방 주인에게 알람 전송
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	private void sendNotificationToRoomOwner(Long roomId) {
		Long roomOwnerUserId = chatRoomRepository.findByUserId(roomId);

		NotificationRequestDto notificationRequestDto =
			new NotificationRequestDto("알람입니다.", "메시지가 도착했습니다.", getNotificationToken(roomOwnerUserId));
		sendNotification(notificationRequestDto);
		log.info("보내졌습니다! (user)");
	}


	/**
	 * fcm 토큰얻기
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public String getNotificationToken(long userId) {
		User user = userRepository.findByUserId(userId);
		NotificationMapperResponse notification = notificationRepository.findByUser(user);
		return notification.getToken();
	}

	/**
	 * fcm 전송
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	public void sendNotification(NotificationRequestDto req){
		try{
			FirebaseMessaging.getInstance().send(Message.builder()
				.setNotification(Notification.builder()
					.setTitle(req.getTitle())
					.setBody(req.getMessage())
					.build())
				.setToken(req.getToken())
				.build());
		}catch (Exception e){
			log.info("Failed to send notification: " + e.getMessage());
		};
	}
}