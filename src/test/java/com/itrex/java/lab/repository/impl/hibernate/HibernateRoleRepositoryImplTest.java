package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.RoleRepository;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class HibernateRoleRepositoryImplTest extends BaseRepositoryTest {
    private final RoleRepository repository;

    public HibernateRoleRepositoryImplTest() {
        super();
        repository = new HibernateRoleRepositoryImpl(getSession());
    }

    @Test
    public void selectAll_validData_shouldReturnExistRolesTest() throws GymException {
        //given && when
        int expected = 2;
        final List<Role> result = repository.selectAll();
        int actual = result.size();

        //then
        assertEquals(actual, expected);

    }

    @Test
    public void selectByID_validData_shouldReturnCorrectRoleTest() throws GymException {
        //given
        int id = 1;
        Role expected = new Role();
        expected.setId(1);
        expected.setName("admin");

        // when
        Optional<Role> optionalRole = repository.selectById(id);
        Role actual = optionalRole.get();
        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectById_invalidData_shouldReturnException() throws GymException {
        //given && when && then
        assertNotNull(repository.selectById(99));
    }

    @Test
    public void add_validData_shouldAddRoleTest() throws GymException {
        //given
        Role expected = new Role();
        expected.setId(3);
        expected.setName("some role");

        //when
        Role actual = repository.add(expected);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void add_invalidData_shouldThrowExceptionTest() {
        //given
        Role expected = new Role();
        expected.setId(3);
        expected.setName(null);

        //when && then
        assertThrows(GymException.class, () -> repository.add(expected));
    }

    @Test
    public void update_validData_shouldReturnChangedRoleTest() throws GymException {
        //given
        Optional<Role> optionalRole = repository.selectById(2);
        Role role = optionalRole.get();

        //when
        role.setName("def");
        repository.update(role);
        String actual = role.getName();
        String expected = "def";

        //then
        assertEquals(expected, actual);
    }
}