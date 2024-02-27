package com.oya.kr.global.support.dto.request;

import java.util.List;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.28
 */
@Getter
@RequiredArgsConstructor
public class SenderRequest {

    private final String from;
    private final List<String> to;
    private final String subject;
    private final String content;

    public SendEmailRequest toSendRequestDto() {
        Destination destination = new Destination()
            .withToAddresses(this.to);

        Message message = new Message()
            .withSubject(createContent(this.subject))
            .withBody(new Body()
                .withHtml(createContent(this.content)));

        return new SendEmailRequest()
            .withSource(this.from)
            .withDestination(destination)
            .withMessage(message);
    }

    private Content createContent(String text) {
        return new Content()
            .withCharset("UTF-8")
            .withData(text);
    }
}
