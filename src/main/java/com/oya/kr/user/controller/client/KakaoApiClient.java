package com.oya.kr.user.controller.client;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.oya.kr.user.controller.dto.request.AccessTokenRequest;
import com.oya.kr.user.controller.dto.response.KakaoInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuth 요청을 위한 Client 클래스
 *
 * @author 이상민
 * @since 2024.02.14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoApiClient {

	/**
	 * Access Token 을 기반으로 Email, Nickname 이 포함된 프로필 정보를 획득
	 *
	 * @param token
	 * @return OAuthInfoResponse
	 * @author 이상민
	 * @since 2024.02.14
	 */
	public KakaoInfo requestOauthInfo(AccessTokenRequest token) {

		URI uri = URI.create("https://kapi.kakao.com/v2/user/me");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token.getAccessToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<KakaoInfo> responseEntity = restTemplate.exchange(
			new RequestEntity<>(headers, HttpMethod.POST, uri), KakaoInfo.class);

		return responseEntity.getBody();
	}
}