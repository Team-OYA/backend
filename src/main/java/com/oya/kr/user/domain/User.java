package com.oya.kr.user.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;

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
    private LocalDate birthDate;
    private Gender gender;
    private RegistrationType registrationType;
    private UserType userType;
    private String businessRegistrationNumber;
}
