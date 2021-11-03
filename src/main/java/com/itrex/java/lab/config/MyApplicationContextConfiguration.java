package com.itrex.java.lab.config;

import com.itrex.java.lab.service.FlywayService;
import com.itrex.java.lab.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.itrex.java.lab")
public class MyApplicationContextConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FlywayService flywayService() {
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();
        return flywayService;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Session session() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
