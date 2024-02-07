package com.oya.kr.test.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oya.kr.global.config.RootConfig;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class TimeMapperTest {

	@Autowired
	private TimeMapper timeMapper;

	@Test
	public void testGetTime() {
		log.info(timeMapper.getClass().getName());
		log.info(timeMapper.getTime());
	}

	@Test
	public void testGetTime2() {
		log.info("getTime2");
		log.info(timeMapper.getClass().getName());
		log.info(timeMapper.getTime2());
	}
}
