package com.itrex.java.lab.repository.config;

import static com.itrex.java.lab.properties.Properties.H2_PASSWORD;
import static com.itrex.java.lab.properties.Properties.H2_URL;
import static com.itrex.java.lab.properties.Properties.H2_USER;
import static com.itrex.java.lab.properties.Properties.MIGRATIONS_LOCATION;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.hibernate.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.itrex.java.lab.repository")
public class TestContextConfiguration {

    @Bean(initMethod = "migrate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Flyway flyway() {
        Flyway flyway = Flyway.configure().dataSource(H2_URL, H2_USER, H2_PASSWORD).locations(MIGRATIONS_LOCATION)
                .load();
        return flyway;
    }

    @Bean
    @DependsOn("flyway")
    public Session session() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory().openSession();
    }

    @Bean
    @DependsOn("flyway")
    public JdbcConnectionPool jdbcConnectionPool() {
        return JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
    }
}