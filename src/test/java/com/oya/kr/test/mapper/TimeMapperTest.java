package com.oya.kr.test.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oya.kr.global.config.RootConfig;

import lombok.extern.java.Log;

@Log
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
public class TimeMapperTest {

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