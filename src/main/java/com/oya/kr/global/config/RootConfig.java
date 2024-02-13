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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.java.Log;

@Configuration
@ComponentScan(basePackages = {"com.oya.kr"})
@PropertySource("classpath:secret/database.properties")
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
		registerEnumOrdinalTypeHandler(sqlSessionFactoryBean);
		return sqlSessionFactoryBean.getObject();
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
		return sqlSessionFactoryBean;
	}

	private void registerEnumOrdinalTypeHandler(SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
		typeHandlerRegistry.register(Gender.class, EnumOrdinalTypeHandler.class);
		typeHandlerRegistry.register(RegistrationType.class, EnumOrdinalTypeHandler.class);
		typeHandlerRegistry.register(UserType.class, EnumOrdinalTypeHandler.class);
	}
}
