package com.itrex.java.lab.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void toString_shouldReturnExistStringTest() {

        //given
        User user = new User();
        user.setLogin("adam");
        user.setPassword("qwerty");
        user.setName("ken");
        user.setSurname("pol");
        user.setPhone("+375295588769");

        //when
        String expected = "User{id=null, login='adam', password='qwerty', name='ken', surname='pol', phone='+375295588769'}";
        String actual = user.toString();

        //then
        assertEquals(expected, actual);
    }
}