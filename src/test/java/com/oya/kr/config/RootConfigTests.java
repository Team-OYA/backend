package com.oya.kr.config;

import static org.junit.Assert.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
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
public class RootConfigTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testConnection() {
		try (Connection con = dataSource.getConnection()) {
			log.info(con.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testMyBatis() {
		try (SqlSession session = sqlSessionFactory.openSession();
			 Connection con = session.getConnection()) {
			log.info(session);
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
