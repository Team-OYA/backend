package com.oya.kr.global.domain;

import static com.oya.kr.global.exception.GlobalErrorCodeList.INVALID_FORMAT_CONTACT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oya.kr.global.exception.ApplicationException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexValidator {

    private static final String contactRegex = "^0(10|11|12|13|14|15|16|17|18|19)-\\d{3,4}-\\d{4}$";
    private static final Pattern pattern = Pattern.compile(contactRegex);

    public static void validateContactInformationIsAppropriateForFormatting(String contactInformation) {
        Matcher matcher = pattern.matcher(contactInformation);
        if (!matcher.matches()) {
            throw new ApplicationException(INVALID_FORMAT_CONTACT);
        }
    }
}
