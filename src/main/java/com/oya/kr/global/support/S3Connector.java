package com.oya.kr.global.support;

import static com.oya.kr.global.exception.GlobalErrorCodeList.FAIL_CONVERT_S3_IMAGE;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.oya.kr.global.exception.ApplicationException;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
@Component
public class S3Connector implements StorageConnector {

    private final AmazonS3 amazonS3;
    private final S3Provider s3Provider;
    private final S3Generator s3Generator;

    public S3Connector(S3Provider s3Provider, S3Generator s3Generator) {
        this.amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
        this.s3Provider = s3Provider;
        this.s3Generator = s3Generator;
    }

    /**
     * S3 이미지 저장
     * @since 2024.02.16
     * @parameter MultipartFile, String, String
     * @return String
     * @author 김유빈
     */
    @Override
    public String save(MultipartFile resource) {
        File file = convertMultiPartToFile(resource);
        String fileName = s3Generator.createResourceName(resource);
        return uploadFileToS3(fileName, file);
    }

    private File convertMultiPartToFile(MultipartFile file) {
        try {
            File convertedFile = new File(file.getOriginalFilename());
            file.transferTo(convertedFile);
            return convertedFile;
        } catch (IOException e) {
            throw new ApplicationException(FAIL_CONVERT_S3_IMAGE);
        }
    }

    private String uploadFileToS3(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(s3Provider.bucket(), fileName, file));
        return amazonS3.getUrl(s3Provider.bucket(), fileName).toString();
    }
}
