package com.itrex.java.lab.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoachTest {

    @Test
    void toString_shouldReturnExistStringTest() {

        //given
        Coach coach = new Coach();
        coach.setName("ken");
        coach.setSurname("pol");
        coach.setPhone("+375295588769");
        coach.setSpecialization("fitness");
        coach.setPrice_of_activity(25.0);

        //when
        String expected = "Coach{id=null, name='ken', surname='pol', phone='+375295588769', specialization='fitness', price_of_activity=25.0}";
        String actual = coach.toString();

        //then
        assertEquals(expected, actual);
    }
}