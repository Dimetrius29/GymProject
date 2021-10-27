package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.impl.jdbc.JDBCRoleRepositoryImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class JDBCRoleRepositoryImplTest extends BaseRepositoryTest {

    private final RoleRepository repository;

    public JDBCRoleRepositoryImplTest() {
        super();
        repository = new JDBCRoleRepositoryImpl(getConnectionPool());
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
    public void selectByID_validData_shouldReturnCorrectRoleTest() throws GymException, NotFoundEx {
        //given
        int id = 1;
        Role expected = new Role();
        expected.setId(1);
        expected.setName("admin");

        // when
        Role actual = repository.selectById(id);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectById_invalidData_shouldReturnException() {
        //given && when && then
        assertThrows(NotFoundEx.class, () -> repository.selectById(99));
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
    public void deleteRole_validData_shouldDeleteExistRole() throws GymException {
        //given
        int id = 1;
        int expected = 1;

        // when
        repository.deleteRole(id);
        List<Role> roles = repository.selectAll();
        int actual = roles.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void update_validData_shouldReturnChangedRoleTest() throws GymException {
        //given
        Role role = new Role();
        role.setId(3);
        role.setName("test");

        //when
        role.setName("def");
        repository.update(role);
        String actual = role.getName();
        String expected = "def";

        //then
        assertEquals(expected, actual);
    }
}