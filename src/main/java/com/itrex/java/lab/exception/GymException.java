package com.itrex.java.lab.exception;

import org.hibernate.HibernateException;

public class GymException extends Exception{
    public GymException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public GymException(Throwable ex) {
        super(ex);
    }
}