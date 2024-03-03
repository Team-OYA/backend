package com.oya.kr.global.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.oya.kr.global.exception.RestTemplateResponseErrorHandler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConnector {

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * RestTemplate 에러 핸들링 객체 할당
     *
     * @author 김유빈
     * @since 2024.02.29
     */
    static {
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

    /**
     * get api 호출
     *
     * @parameter String, HttpEntity<?>, Class<T>
     * @return ResponseEntity<T>
     * @author 김유빈
     * @since 2024.02.29
     */
    public static <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    /**
     * post api 호출
     *
     * @parameter String, Object, Class<T>
     * @return ResponseEntity<T>
     * @author 김유빈
     * @since 2024.02.29
     */
    public static <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType) {
        return restTemplate.postForEntity(url, request, responseType);
    }
}
