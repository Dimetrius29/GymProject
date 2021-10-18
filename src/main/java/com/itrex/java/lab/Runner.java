package com.itrex.java.lab;

import static com.itrex.java.lab.properties.Properties.H2_PASSWORD;
import static com.itrex.java.lab.properties.Properties.H2_URL;
import static com.itrex.java.lab.properties.Properties.H2_USER;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.impl.JDBCUserRepositoryImpl;
import com.itrex.java.lab.service.FlywayService;
import java.util.ArrayList;
import java.util.List;
import org.h2.jdbcx.JdbcConnectionPool;

public class Runner {

    public static void main(String[] args) {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        System.out.println("============CREATE CONNECTION POOL================");
        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);

        System.out.println("=============CREATE UserRepository================");
        UserRepository userRepository = new JDBCUserRepositoryImpl(jdbcConnectionPool);
        List<User> users = userRepository.selectAll();
        System.out.println("All users:\n" + users);

        User firstUser = new User();
        firstUser.setLogin("Ron");
        firstUser.setPassword("fd");
        firstUser.setName("Pavel");
        firstUser.setSurname("Signor");
        firstUser.setPhone("+375345855685");
        userRepository.add(firstUser);
        System.out.println("First added user:\n" + firstUser);

        User secondUser = new User();
        secondUser.setLogin("ever");
        secondUser.setPassword("party");
        secondUser.setName("Istvan");
        secondUser.setSurname("Danilov");
        secondUser.setPhone("+375299633538");

        userRepository.add(secondUser);
        System.out.println("Second added user:\n" + secondUser);

        users = userRepository.selectAll();
        System.out.println("All users:\n" + users);
        List<User> newUsers = new ArrayList<>();
        User newUser = new User();
        newUser.setLogin("Brien");
        newUser.setPassword("Nick");
        newUser.setName("Niek");
        newUser.setSurname("Bri3n");
        newUser.setPhone("+375334548977");

        newUsers.add(newUser);
        userRepository.addAll(newUsers);
        System.out.println("New users:\n" + newUsers);

        users = userRepository.selectAll();
        System.out.println("All users:\n" + users);

        newUser.setSurname("Brien");
        userRepository.update(newUser);
        users = userRepository.selectAll();
        System.out.println("Updated newUser:\n" + users);

        userRepository.delete(6);
        System.out.println("User with id=6 deleted");

        users = userRepository.selectAll();
        System.out.println("All users:\n" + users);

        System.out.println("=========CLOSE ALL UNUSED CONNECTIONS=============");
        jdbcConnectionPool.dispose();
        System.out.println("=================SHUT DOWN APP====================");
    }
}
