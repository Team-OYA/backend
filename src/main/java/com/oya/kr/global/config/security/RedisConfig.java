package com.oya.kr.global.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author 이상민
 * @since 2024.02.23
 */
@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String REDIS_HOST;
	@Value("${spring.redis.port}")
	private int REDIS_PORT;

	/**
	 * RedisConnectionFactory 빈 등록
	 *
	 * @return RedisConnectionFactory
	 * @author 이상민
	 * @since 2024.02.15
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(REDIS_HOST, REDIS_PORT);
	}

	/**
	 * RedisTemplate 빈 등록
	 *
	 * @param redisConnectionFactory
	 * @return RedisTemplate<Object, Object>
	 * @author 이상민
	 * @since 2024.02.15
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setDefaultSerializer(RedisSerializer.json());
		template.setKeySerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
}
