package com.oya.kr.global.support;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.oya.kr.global.support.dto.request.SenderRequest;

/**
 * @author 김유빈
 * @since 2024.02.28
 */
@Qualifier("SESSender")
@Component
public class SESSender implements MailSender {

    private final String accessKey;
    private final String secretKey;
    private final String region;

    public SESSender(
        @Value("${aws.ses.access-key}") String accessKey,
        @Value("${aws.ses.secret-key}") String secretKey,
        @Value("${aws.ses.region}") String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    /**
     * AWS SES 를 이용하여 메일 발송
     *
     * @author 김유빈
     * @since 2024.02.28
     */
    @Override
    public void send(SenderRequest request) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(region).build();
        client.sendEmail(request.toSendRequestDto());
    }
}
