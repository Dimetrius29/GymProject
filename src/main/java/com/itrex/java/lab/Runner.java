package com.itrex.java.lab;

import static com.itrex.java.lab.properties.Properties.H2_PASSWORD;
import static com.itrex.java.lab.properties.Properties.H2_URL;
import static com.itrex.java.lab.properties.Properties.H2_USER;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.impl.JDBCUserRepositoryImpl;
import com.itrex.java.lab.service.FlywayService;

import org.h2.jdbcx.JdbcConnectionPool;

public class Runner {

    public static void main(String[] args) throws GymException, NotFoundEx {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        System.out.println("============CREATE CONNECTION POOL================");
        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);

        System.out.println("=============CREATE UserRepository================");

        UserRepository userRepository = new JDBCUserRepositoryImpl(jdbcConnectionPool);

        User firstUser = new User();
        firstUser.setLogin("Ron");
        firstUser.setPassword("fd");
        firstUser.setName("Pavel");
        firstUser.setSurname("Signor");
        firstUser.setPhone("+375345855685");
        User secondUser = new User();
        secondUser.setLogin("ever");
        secondUser.setPassword("party");
        secondUser.setName("Istvan");
        secondUser.setSurname("Danilov");
        secondUser.setPhone("+375299633538");
        userRepository.add(firstUser);
        System.out.println(userRepository.selectById(2));
        userRepository.add(secondUser);
        System.out.println("Second added user:\n" + secondUser);
        System.out.println("ALL USERS: " + userRepository.selectAll());
        userRepository.delete(1);

        System.out.println("ALL USERS WITHOUT USER ID=1: " + userRepository.selectAll());

        System.out.println("Clients: \n" + userRepository.getAllUsersByRole("client"));
        System.out.println("=========CLOSE ALL UNUSED CONNECTIONS=============");
        jdbcConnectionPool.dispose();
        System.out.println("=================SHUT DOWN APP====================");
    }
}
