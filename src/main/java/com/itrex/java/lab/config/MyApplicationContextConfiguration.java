package com.itrex.java.lab.config;

import static com.itrex.java.lab.properties.Properties.H2_PASSWORD;
import static com.itrex.java.lab.properties.Properties.H2_URL;
import static com.itrex.java.lab.properties.Properties.H2_USER;
import static com.itrex.java.lab.properties.Properties.MIGRATIONS_LOCATION;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.itrex.java.lab")
public class MyApplicationContextConfiguration {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure().dataSource(H2_URL, H2_USER, H2_PASSWORD).locations(MIGRATIONS_LOCATION).load();
    }

    @Bean
    @DependsOn("flyway")
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    @DependsOn("flyway")
    public JdbcConnectionPool jdbcConnectionPool() {
        return JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
    }
}