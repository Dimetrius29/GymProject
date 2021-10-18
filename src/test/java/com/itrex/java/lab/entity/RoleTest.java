package com.itrex.java.lab.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    void toString_shouldReturnExistStringTest() {

        //given
        Role role = new Role();
        role.setName("admin");

        //when
        String expected = "Role{id=null, name='admin'}";
        String actual = role.toString();

        //then
        assertEquals(expected, actual);
    }
}
