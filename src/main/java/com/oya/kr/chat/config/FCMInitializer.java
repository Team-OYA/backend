package com.oya.kr.chat.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 이상민
 * @since 2024.03.07
 */
@Service
@Slf4j
public class FCMInitializer {

	private static final String FIREBASE_CONFIG_PATH = "firebase.json";
	private static boolean initialized = false;

	/**
	 * fcm 초기화
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	@PostConstruct
	public void initialize() {
		if (!initialized) {
			try {
				GoogleCredentials googleCredentials = GoogleCredentials
					.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream());
				FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(googleCredentials)
					.build();
				FirebaseApp.initializeApp(options);
				initialized = true;
			} catch (IOException e) {
				log.info("FCM 오류");
				log.error("FCM 오류 메시지: " + e.getMessage());
			}
		} else {
			log.warn("FirebaseApp 이름 [DEFAULT]으로 초기화된 앱이 이미 존재합니다.");
		}
	}
}
