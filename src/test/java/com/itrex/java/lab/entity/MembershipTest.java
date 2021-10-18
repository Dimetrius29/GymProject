package com.itrex.java.lab.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MembershipTest {

    @Test
    void toString_shouldReturnExistStringTest() {

        //given
        Membership membership = new Membership();
        membership.setUser(new User());
        membership.setDiscount(10);
        membership.setExpirationDate(new Date(121212l));

        //when
        String expected = "Membership{user=User{id=null, login='null', password='null', name='null', surname='null', phone='null'}, discount=10, expirationDate=Thu Jan 01 03:02:01 MSK 1970}";
        String actual = membership.toString();

        //then
        assertEquals(expected, actual);
    }
}
