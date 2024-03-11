package com.oya.kr.chat.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.DefaultResourceLoader;

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

	private static boolean initialized = false;

	/**
	 * fcm 초기화
	 *
	 * @author 이상민
	 * @since 2024.03.07
	 */
	@PostConstruct
	public void initialize() throws IOException {
		if (!initialized) {
			try {
				// EC2 root 디렉토리에 있는 파일에 접근하기 위해 FileSystemResource를 사용
				Resource resource = new FileSystemResource("/home/ubuntu/firebase.json");

				byte[] jsonBytes = getJsonBytes(resource);
				String jsonContent = new String(jsonBytes, StandardCharsets.UTF_8);

				log.info("Firebase JSON 파일 내용:\n{}", jsonContent);

				GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ByteArrayInputStream(jsonBytes))
					.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
				FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(googleCredentials)
					.build();
				FirebaseApp.initializeApp(options);
				initialized = true;
				log.info("FCM 초기화 성공");
			} catch (IOException e) {
				log.info("FCM 오류");
				log.error("FCM 오류 메시지: " + e.getMessage(), e);
			}
		} else {
			log.warn("FirebaseApp 이름 [DEFAULT]으로 초기화된 앱이 이미 존재합니다.");
		}
	}

	private byte[] getJsonBytes(Resource resource) throws IOException {
		try (InputStream inputStream = resource.getInputStream()) {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toByteArray();
		}
	}

}
