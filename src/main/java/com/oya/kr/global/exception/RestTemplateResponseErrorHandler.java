package com.oya.kr.global.exception;

import static com.oya.kr.global.exception.GlobalErrorCodeList.HTTP_CLIENT_ERROR;
import static com.oya.kr.global.exception.GlobalErrorCodeList.HTTP_SERVER_ERROR;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import lombok.extern.java.Log;

/**
 * @author 김유빈
 * @since 2024.02.29
 */
@Log
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    /**
     * RestTemplate 에러 핸들링
     *
     * @parameter ClientHttpResponse
     * @author 김유빈
     * @since 2024.02.29
     */
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        log.info(String.format("[외부 API 연동 실패] %d %s",
            httpResponse.getRawStatusCode(), httpResponse.getStatusText()));

        if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new ApplicationException(HTTP_SERVER_ERROR);
        } else if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new ApplicationException(HTTP_CLIENT_ERROR);
        }
    }
}
