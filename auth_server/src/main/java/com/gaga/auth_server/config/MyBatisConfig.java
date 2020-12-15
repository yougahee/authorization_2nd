package com.gaga.auth_server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan("com.gaga.auth_server.mapper")
public class MyBatisConfig {

    private static final String URL = "localhost";
    private static final int DB_PORT = 3306;
    private static final String DATABASE_NAME = "authorization";
    private static final String CONNECTION_OPTION = "useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "test";

    /*
    SqlSession -> RDB에 인증을 거친 논리적인 연결 상태
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //configuration 인스턴스를 만들어서 직접 지정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setConfiguration(configuration);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml");
        return factoryBean.getObject();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver);
        dataSource.setUrl(String.format("jdbc:mysql://%s:%d/%s?%s",
                URL, DB_PORT, DATABASE_NAME, CONNECTION_OPTION));
        dataSource.setUsername(DB_USER_NAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
