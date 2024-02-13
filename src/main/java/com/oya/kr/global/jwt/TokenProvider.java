package com.oya.kr.global.jwt;

import static com.oya.kr.global.exception.GlobalErrorCodeList.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.oya.kr.global.exception.ApplicationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenProvider {

	private final JwtProperties jwtProperties;

	// private final RedisDao redisDao;

	public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
	private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);

	public String generateToken(com.oya.kr.user.domain.User user, Duration expiredAt){
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}

	// refreshToken 생성
	public String createRefreshToken(com.oya.kr.user.domain.User user){
		String refreshToken = generateToken(user, REFRESH_TOKEN_DURATION);
		// redis에 저장
		return refreshToken;
	}

	private String makeToken(Date expiry, com.oya.kr.user.domain.User user) {
		Date now = new Date();

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuer(jwtProperties.getIssuer())
			.setIssuedAt(now)
			.setExpiration(expiry)
			.setSubject(user.getEmail())
			.claim("id", user.getId())
			.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretkey())
			.compact(); // JWT 토큰 생성
	}

	public boolean validToken(String token){
		try {
			Jwts.parser()
				.setSigningKey(jwtProperties.getSecretkey())
				.parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
			throw new ApplicationException(INVALID_TOKEN);
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			throw new ApplicationException(INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			log.info("만료된 token");
			throw new ApplicationException(EXPIRE_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 token");
			throw new ApplicationException(UNSUPPORTED_TOKEN);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims is empty, 잘못된 토큰입니다.");
			throw new ApplicationException(WRONG_TOKEN);
		}
	}

	// 토큰 기반으로 인증 정보를 가져오는 메서드
	public Authentication getAuthentication(String token){
		Claims claims = getClaims(token);
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
		return new UsernamePasswordAuthenticationToken(new User(claims.getSubject(), "", authorities), token, authorities);
	}

	// 토큰 기반으로 유저 ID를 가져오는 메서드
	public Long getUserId(String token){
		Claims claims = getClaims(token);
		return claims.get("id", Long.class);
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(jwtProperties.getSecretkey())
			.parseClaimsJws(token)
			.getBody();
	}

}
