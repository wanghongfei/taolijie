package com.fh.taolijie.test;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by wanghongfei on 15-4-10.
 */
@Configuration
@ComponentScan(value = {
        "com.fh.taolijie.controller",
        "com.fh.taolijie.service",
        "com.fh.taolijie.dao.mapper",
})
@MapperScan( annotationClass = Repository.class, basePackages = "com.fh.taolijie.dao.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MySqlConfigBean {
    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUsername("root");
        ds.setPassword("111111");
        ds.setUrl("jdbc:mysql://localhost:3306/taolijie?allowMultiQueries=true");
        ds.setDriverClassName("com.mysql.jdbc.Driver");

        return ds;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager tx = new DataSourceTransactionManager();
        tx.setDataSource(this.dataSource);
        return tx;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean session = new SqlSessionFactoryBean();
        session.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        session.setDataSource(this.dataSource);

        return session;
    }

/*    @Bean
    public MapperScannerConfigurer scanner() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setAnnotationClass(Repository.class);
        scanner.setBasePackage("com.fh.purelove.dao.mapper");
        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");

        return scanner;
    }*/
}
