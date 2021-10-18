package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JDBCUserRepositoryImplTest extends BaseRepositoryTest {


    private final UserRepository repository;

    public JDBCUserRepositoryImplTest() {
        super();
        repository = new JDBCUserRepositoryImpl(getConnectionPool());
    }

    @Test
    public void selectAll_validData_shouldReturnExistUser() {
        //given && when
        final List<User> result = repository.selectAll();

        //then
        assertFalse(result.isEmpty());
    }

    @Test
    void add_user_shouldAddUserTest() {
        //given
        User expected = new User();
        expected.setId(repository.selectAll().size() + 1);
        expected.setLogin(expected.getLogin());
        expected.setPassword(expected.getPassword());
        expected.setName(expected.getName());
        expected.setSurname(expected.getSurname());
        expected.setPhone(expected.getPhone());

        //when
        User actual = repository.add(expected);

        //then
        assertEquals(expected, actual);
    }

//    @Test
//    void update_validData_receiveUserAndInteger_shouldReturnExistUserTest() {
//        //given
//        User user = new User();
//        user.setId(repository.selectAll().size());
//        user.setLogin(user.getLogin());
//        user.setPassword(user.getPassword());
//        user.setName(user.getName());
//        user.setSurname(user.getSurname());
//        user.setPhone(user.getPhone());
//
//        //when
//        String actual = user.getName();
//        user.setName("Pashka");
//        repository.update(user);
//        String expected = "Pashka";
//
//        //then
//        assertEquals(expected, actual);
//    }
}

