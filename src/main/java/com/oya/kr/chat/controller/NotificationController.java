package com.oya.kr.chat.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oya.kr.chat.service.NotificationService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.03.07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

	private final NotificationService notificationService;

	/**
	 * fcm 토큰 저장
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	@PostMapping("/new")
	public void saveNotification(Principal principal, @RequestBody Map<String, Object> request) {
		String token = (String) request.get("token");
		notificationService.saveNotification(principal.getName(), token);
	}
}