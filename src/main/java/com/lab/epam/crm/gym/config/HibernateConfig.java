package com.lab.epam.crm.gym.config;

import lombok.experimental.FieldDefaults;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@ComponentScan(basePackages = "com.lab.epam.crm.gym")
@FieldDefaults(level = PRIVATE)
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties",
                ignoreResourceNotFound = true)})
@EnableJpaRepositories(basePackages = "com.lab.epam.crm.gym.repository")
public class HibernateConfig {

    @Value("${spring.datasource.url}")
    String dataSourceUrl;

    @Value("${spring.datasource.username}")
    String dataSourceUser;

    @Value("${spring.datasource.password}")
    String dataSourcePassword;

    @Value("${spring.jpa.hibernate.dialect}")
    String hibernateDialect;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String hibernateDdlAuto;

    @Value("${spring.jpa.show-sql}")
    String hibernateShowSql;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.lab.epam.crm.gym.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUser(dataSourceUser);
        dataSource.setPassword(dataSourcePassword);

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        properties.put("hibernate.show_sql", hibernateShowSql);

        return properties;
    }
}