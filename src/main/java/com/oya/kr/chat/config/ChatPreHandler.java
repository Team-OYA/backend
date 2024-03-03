package com.oya.kr.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.oya.kr.global.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.03.03
 */
@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String BEARER_PREFIX = "Bearer ";

	private final TokenProvider tokenProvider;

	/**
	 * websocket을 통해 들어온 요청이 처리 되기전 실행
	 *
	 * @author 이상민
	 * @since 2024.03.03
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청
			String jwtToken = accessor.getFirstNativeHeader("Authorization");
			logger.info("CONNECT {}", jwtToken);
			// Header의 jwt token 검증
			String token = jwtToken.substring(BEARER_PREFIX.length());
			tokenProvider.validToken(token);
		}
		return message;
	}
}
