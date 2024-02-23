package com.oya.kr.global.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author 이상민
 * @since 2024.02.23
 */
@Repository
public class RedisRepository {

	private final RedisTemplate<Object, Object> redisTemplate;

	public RedisRepository(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void saveData(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public Object getData(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteData(String key) {
		redisTemplate.delete(key);
	}
}
