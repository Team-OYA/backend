package com.oya.kr.global.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
@Component
public class S3Provider implements StorageProvider {

    private final String bucket;

    public S3Provider(
        @Value("${spring.cloud.aws.s3.bucket}") String bucket
    ) {
        this.bucket = bucket;
    }

    @Override
    public String bucket() {
        return bucket;
    }
}
