package com.itrex.java.lab;

import com.itrex.java.lab.config.MyApplicationContextConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyApplicationContextConfiguration.class);

    }
}
