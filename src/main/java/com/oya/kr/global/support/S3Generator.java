package com.oya.kr.global.support;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
@Component
public class S3Generator implements StorageGenerator {

    /**
     * 이미지 이름 생성
     *
     * @since 2024.02.16
     * @parameter MultipartFile
     * @return String
     * @author 김유빈
     */
    @Override
    public String createResourceName(MultipartFile resource) {
        final String filename = resource.getOriginalFilename();
        final String extension = filename.substring(filename.lastIndexOf(".") + 1);
        final String resourceName = UUID.randomUUID().toString().replace("-", "");
        return String.join(".", resourceName, extension);
    }
}
