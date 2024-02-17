package com.oya.kr.global.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.java.Log;

/**
 * @author 이상민
 * @since 2024.02.12
 */
@Configuration
@ComponentScan(basePackages = {"com.oya.kr"})
@PropertySource("classpath:application.properties")
@MapperScan(basePackages = {"com.oya.kr"})
@Log
public class RootConfig {

	@Value("${datasource.driver-class-name}")
	private String driverClassName;

	@Value("${datasource.jdbc-url}")
	private String jdbcUrl;

	@Value("${datasource.username}")
	private String username;

	@Value("${datasource.password}")
	private String password;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(jdbcUrl);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setConfigLocation(
			applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
		return sqlSessionFactoryBean;
	}
}
