package com.oya.kr.chat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.02.28
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.oya.kr.global"})
public class StompConfig implements WebSocketMessageBrokerConfigurer {

	private final ChatPreHandler chatPreHandler;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic"); //sub으로 시작되는 요청을 구독한 모든 사용자들에게 메시지를 broadcast한다.
		config.setApplicationDestinationPrefixes("/app"); // pub로 시작되는 메시지는 message-handling methods로 라우팅된다.
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.setAllowedOrigins("*")
			.withSockJS(); // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할수 있게 설정
		// registry.addEndpoint("/ws").setAllowedOrigins("*"); // api 통신 시, withSockJS() 설정을 빼야됨
	}

	/**
	 * 인증된 사용자만 받기
	 *
	 * @author 이상민
	 * @since 2024.03.03
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(chatPreHandler);
	}
}