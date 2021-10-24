package com.itrex.java.lab.exception;

public class GymException extends Exception{
    public GymException(String msg, Throwable ex) {
        super(msg, ex);
    }
}