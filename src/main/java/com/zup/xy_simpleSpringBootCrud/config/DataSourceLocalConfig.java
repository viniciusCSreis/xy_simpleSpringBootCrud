package com.zup.xy_simpleSpringBootCrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = "com.zup.xy_simpleSpringBootCrud.repositoryLocal",
        entityManagerFactoryRef = "localEntityManager",
        transactionManagerRef = "localTransactionManager"
)
public class DataSourceLocalConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean localEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localDataSource());
        em.setPackagesToScan( new String[] { "com.zup.xy_simpleSpringBootCrud.modelLocal" });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public DataSource localDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.local.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.local.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.local.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.local.password"));
        return dataSource;

    }

    @Bean
    public PlatformTransactionManager localTransactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( localEntityManager().getObject());
        return transactionManager;
    }
}
