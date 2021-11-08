package com.itrex.java.lab;

import com.itrex.java.lab.config.MyApplicationContextConfiguration;

import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.impl.hibernate.HibernateUserRepositoryImpl;
import com.itrex.java.lab.service.UserService;
import com.itrex.java.lab.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) throws GymException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyApplicationContextConfiguration.class);
    }
}
