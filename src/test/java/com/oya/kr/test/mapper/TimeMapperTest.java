package com.oya.kr.test.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;

import lombok.extern.java.Log;

@Log
public class TimeMapperTest extends SpringApplicationTest {

	@Autowired
	private TimeMapper timeMapper;

	@Test
	public void testGetTime() {
		// given

		// when
		log.info("Given class: " + timeMapper.getClass().getName());
		String result = timeMapper.getTime();

		// then
		log.info("Current time: " + result);
		assertNotNull(result);
	}

	@Test
	public void testGetTime2() {

		// when
		log.info("Given class: " + timeMapper.getClass().getName());
		String result = timeMapper.getTime2();

		// then
		log.info("Result from getTime2: " + result);
		assertNotNull(result);
	}
}