package com.oya.kr.global.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class JwtProperties {
	@Value("${jwt.issuer}")
	private String issuer;
	@Value("${jwt.secret_key}")
	private String secretkey;
}
