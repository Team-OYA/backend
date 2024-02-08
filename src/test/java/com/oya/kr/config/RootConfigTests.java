package com.oya.kr.config;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
public class RootConfigTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testConnection() {
		assertDoesNotThrow(() -> {
			try (Connection con = dataSource.getConnection()) {
				log.info(con.toString());
			}
		});
	}

	@Test
	public void testMyBatis() {
		assertDoesNotThrow(() -> {
			try (SqlSession session = sqlSessionFactory.openSession();
				 Connection con = session.getConnection()) {
				log.info(session.toString());
				log.info(con.toString());
			}
		}, "예외가 발생하면 실패합니다.");
	}
}
