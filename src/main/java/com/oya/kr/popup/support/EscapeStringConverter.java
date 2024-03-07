package com.oya.kr.popup.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author 김유빈
 * @since 2024.03.07
 */
@Component
public class EscapeStringConverter {

    private static final Map<String, String> strings = new HashMap<>();

    /**
     * static 블록에 특수 문자 배열 생성
     *
     * @author 김유빈
     * @since 2024.03.07
     */
    static {
        strings.put("&nbsp;", " ");
        strings.put("&lt;", "<");
        strings.put("&gt;", ">");
        strings.put("&amp;", "&");
        strings.put("&quot;", "\"");
        strings.put("&#035;", "#");
        strings.put("&#039;", "'");
    }

    /**
     * 특수 문자를 일반 문자로 대체
     *
     * @parameter String
     * @return String
     * @author 김유빈
     * @since 2024.03.07
     */
    public String convert(String origin) {
        String result = origin;

        for (Map.Entry<String, String> string : strings.entrySet()) {
            result = result.replace(string.getKey(), string.getValue());
        }

        return result;
    }
}
