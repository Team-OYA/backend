package com.oya.kr.popup.support;

import org.springframework.stereotype.Component;

import com.oya.kr.popup.support.dto.response.MailResponse;

/**
 * @author 김유빈
 * @since 2024.03.10
 */
@Component
public class PlanMailTemplate {

    /**
     * 사업계획서 대기 메일 메시지 작성
     *
     * @parameter String
     * @return MailResponse
     * @author 김유빈
     * @since 2024.03.10
     */
    public MailResponse messageForWait(String nickname) {
        return new MailResponse(
            "[THEPOP] 제안해주신 사업계획서가 대기 상태로 전환되었습니다.",
            String.format("<!DOCTYPE html>\n"
                + "<html lang=\"ko\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "</head>\n"
                + "<body>\n"
                + "    <h2>제안해주신 사업계획서가 대기 상태로 전환되었습니다.</h2>"
                + "    <p>%s 고객님, 안녕하세요.</p>\n"
                + "    <p>제안해주신 사업계획서가 대기 상태로 전환되었습니다.</p>\n"
                + "    <p>대기 상태에는 커뮤니티 게시글을 자유롭게 작성하실 수 있습니다.</p>\n"
                + "    <p>이후 제안이 승인되면 팝업스토어 게시글을 올려 팝업스토어를 홍보하실 수 있습니다.</p>\n"
                + "    <p>팝업스토어 제안에 감사드립니다.</p>\n"
                + "    <p>THEPOP 드림</p>\n"
                + "</body>\n"
                + "</html>\n", nickname));
    }

    /**
     * 사업계획서 승인 메일 메시지 작성
     *
     * @parameter String
     * @return MailResponse
     * @author 김유빈
     * @since 2024.03.10
     */
    public MailResponse messageForApprove(String nickname) {
        return new MailResponse(
            "[THEPOP] 제안해주신 사업계획서가 승인되었습니다.",
            String.format("<!DOCTYPE html>\n"
                + "<html lang=\"ko\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "</head>\n"
                + "<body>\n"
                + "    <h2>제안해주신 사업계획서가 승인되었습니다.</h2>"
                + "    <p>%s 고객님, 안녕하세요.</p>\n"
                + "    <p>팝업스토어 입점을 축하드립니다!</p>\n"
                + "    <p>사업계획서가 승인되어 팝업스토어 게시글을 올려 팝업스토어를 홍보하실 수 있습니다.</p>\n"
                + "    <p>팝업스토어 제안에 감사드립니다.</p>\n"
                + "    <p>THEPOP 드림</p>\n"
                + "</body>\n"
                + "</html>\n" , nickname));
    }

    /**
     * 사업계획서 철회 메일 메시지 작성
     *
     * @parameter String
     * @return MailResponse
     * @author 김유빈
     * @since 2024.03.10
     */
    public MailResponse messageForDeny(String nickname) {
        return new MailResponse(
            "[THEPOP] 제안해주신 사업계획서가 거절되었습니다.",
            String.format(
                "<!DOCTYPE html>\n"
                    + "<html lang=\"ko\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "    <h2>제안해주신 사업계획서가 거절되었습니다.</h2>"
                    + "    <p>%s 고객님, 안녕하세요.</p>\n"
                    + "    <p>안타깝지만 제안해주신 사업계획서는 현대백화점과 함께하지 못하게 되었습니다.</p>\n"
                    + "    <p>이후에 더 좋은 사업계획서가 있다면 언제든 연락 바랍니다.</p>\n"
                    + "    <p>팝업스토어 제안에 감사드립니다.</p>\n"
                    + "    <p>THEPOP 드림</p>\n"
                    + "</body>\n"
                    + "</html>\n" , nickname)
        );
    }
}
