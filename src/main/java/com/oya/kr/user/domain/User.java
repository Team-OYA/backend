package com.oya.kr.user.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.enums.Gender;
import com.oya.kr.user.domain.enums.RegistrationType;
import com.oya.kr.user.domain.enums.UserType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Base {

    private String nickname;
    private String email;
    private String password;
    private Date birthDate;
    private Gender gender;
    private RegistrationType registrationType;
    private UserType userType;
    private String businessRegistrationNumber;
    private String profileUrl;

    public static String encodePassword(BCryptPasswordEncoder bCryptPasswordEncoder, String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * 비밀번호 확인
     *
     * @return boolean
     * @author 이상민
     * @since 2024.02.13
     */
    public boolean checkPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}
