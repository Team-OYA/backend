package com.oya.kr.global.support;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
public interface StorageGenerator {

    String createPath();

    String createResourceName(MultipartFile resource);
}
