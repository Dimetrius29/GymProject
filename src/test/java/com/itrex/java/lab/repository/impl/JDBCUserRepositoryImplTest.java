package com.itrex.java.lab.repository.impl;

import static org.junit.Assert.assertEquals;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JDBCUserRepositoryImplTest extends BaseRepositoryTest {

    private final UserRepository repository;

    public JDBCUserRepositoryImplTest() {
        super();
        repository = new JDBCUserRepositoryImpl(getConnectionPool());
    }

    @Test
    public void selectAll_validData_shouldReturnExistUsersTest() {
        //given && when
        int expected = 4;
        final List<User> result = repository.selectAll();
        int actual = result.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectByID_validData_shouldReturnCorrectUserTest() {
        //given
        int id = 1;
        User expected = new User();
        expected.setId(1);
        expected.setLogin("admin");
        expected.setPassword("admin");
        expected.setName("Pavel");
        expected.setSurname("Smirnov");
        expected.setPhone("+375445855685");

        // when
        User actual = repository.selectById(id);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void add_validData_shouldAddUserTest() {
       //given
        ArrayList<User> expected = new ArrayList<>();
        User user1 = new User();
        user1.setId(5);
        user1.setLogin("ret");
        user1.setPassword("qwerty");
        user1.setName("Pol");
        user1.setSurname("Shone");
        user1.setPhone("+37544585645");

        User user2 = new User();
        user2.setId(6);
        user2.setLogin("ret");
        user2.setPassword("qwerty");
        user2.setName("Pol");
        user2.setSurname("Shone");
        user2.setPhone("+37544585645");
        expected.add(user1);
        expected.add(user2);

        //when
        repository.addAll(expected);
        List <User> actual = repository.selectAll();

        //then
        assertEquals(actual.size(), 6);
    }

    @Test
    public void addALL_validData_shouldAddALLUsersTest() {
        //given
        User expected = new User();
        expected.setId(5);
        expected.setLogin("ret");
        expected.setPassword("qwerty");
        expected.setName("Pol");
        expected.setSurname("Shone");
        expected.setPhone("+37544585645");

        //when
        User actual = repository.add(expected);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void deleteUser_validData_shouldDeleteExistUser() {
        //given
        int id = 1;
        int expected = 3;

        // when
        repository.delete(id);
        List<User> users = repository.selectAll();
        int actual = users.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void update_validData_shouldReturnChangedUserTest() {
        //given
        User user = new User();
        user.setId(5);
        user.setLogin("test");
        user.setPassword("test");
        user.setName("test");
        user.setSurname("test");
        user.setPhone("test");

        //when
        user.setName("Pashka");
        repository.update(user);
        String actual = user.getName();
        String expected = "Pashka";

        //then
        assertEquals(expected, actual);
    }
}