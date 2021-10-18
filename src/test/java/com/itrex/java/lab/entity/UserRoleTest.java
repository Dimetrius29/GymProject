package com.itrex.java.lab.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRoleTest {

    @Test
    void toString_shouldReturnExistStringTest() {

        //given
        UserRole userRole = new UserRole();
        userRole.setUser(new User());
        userRole.setRole(new Role());

        //when
        String expected = "UserRole{user=User{id=null, login='null', password='null', name='null', surname='null', phone='null'}, role=Role{id=null, name='null'}}";
        String actual = userRole.toString();

        //then
        assertEquals(expected, actual);
    }
}
