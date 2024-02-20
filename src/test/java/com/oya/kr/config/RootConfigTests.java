package com.oya.kr.config;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oya.kr.common.SpringApplicationTest;

import lombok.extern.java.Log;

/**
 * @author 이상민
 * @since 2024.02.14
 */
@Log
public class RootConfigTests extends SpringApplicationTest {

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

	/**
	 * @author 이상민
	 * @since 2024.02.12
	 */
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
