package com.itrex.java.lab.entity;

import java.util.Date;

public class Membership {

    private User user;
    private Integer discount;
    private Date expirationDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "user=" + user +
                ", discount=" + discount +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
