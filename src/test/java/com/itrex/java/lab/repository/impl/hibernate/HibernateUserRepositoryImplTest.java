package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.UserRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HibernateUserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepository repository;

    public HibernateUserRepositoryImplTest() {
        super();
        repository = new HibernateUserRepositoryImpl(getSession());
    }

    @Test
    public void selectAll_validData_shouldReturnExistUsersTest() throws GymException {
        //given && when
        int expected = 4;
        final List<User> result = repository.selectAll();
        int actual = result.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectByID_validData_shouldReturnCorrectUserTest() throws GymException, NotFoundEx {
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
    public void assignRole_validData_ShouldAddRoleForUserTest() throws GymException, NotFoundEx {
        // when
        repository.assignRole(1, 2);

        // then
        assertEquals(5, getSession().createSQLQuery("SELECT * FROM user_role;")
                .getResultList().size());
    }

    @Test
    public void getAllUsersByRole_validData_shouldReturnExceptionTest() {
        //given && when && then
        cleanDB();
        assertThrows(GymException.class, () -> repository.getAllUsersByRole("admin"));
    }

    @Test
    public void selectById_invalidData_shouldReturnException() {
        //given && when && then
        assertThrows(NotFoundEx.class, () -> repository.selectById(99));
    }

    @Test
    public void addAll_validData_shouldAddAllUsersTest() throws GymException {
        //given
        ArrayList<User> expected = new ArrayList<>();
        User user1 = new User();
        user1.setId(5);
        user1.setLogin("ret4");
        user1.setPassword("qwerty");
        user1.setName("Polk");
        user1.setSurname("Shone");
        user1.setPhone("+3754477445");

        User user2 = new User();
        user2.setId(6);
        user2.setLogin("ret");
        user2.setPassword("qwerty");
        user2.setName("Pol");
        user2.setSurname("Shone");
        user2.setPhone("+37544588845");
        expected.add(user1);
        expected.add(user2);

        //when
        repository.addAll(expected);
        List<User> expectedUsers = repository.selectAll();

        //then
        assertEquals(expectedUsers.size(), 6);
    }

    @Test
    public void add_validData_shouldAddUserTest() throws GymException {
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
    public void add_invalidData_shouldThrowExceptionTest() {
        //given
        User expected = new User();
        expected.setId(5);
        expected.setLogin(null);
        expected.setPassword("qwerty");
        expected.setName("Pol");
        expected.setSurname("Shone");
        expected.setPhone("+37544585645");

        //when && then
        assertThrows(GymException.class, () -> repository.add(expected));
    }

    @Test
    public void deleteUser_validData_shouldDeleteExistUser() throws GymException, NotFoundEx {
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
    public void update_validData_shouldReturnChangedUserTest() throws GymException {
        //given
        User user = new User();
        user.setId(5);
        user.setLogin("test");
        user.setPassword("test");
        user.setName("test");
        user.setSurname("test");
        user.setPhone("test");

        //when
        repository.add(user);
        user.setName("Pavel");
        repository.update(user);
        String actual = user.getName();
        String expected = "Pavel";

        //then
        assertEquals(expected, actual);
    }
}