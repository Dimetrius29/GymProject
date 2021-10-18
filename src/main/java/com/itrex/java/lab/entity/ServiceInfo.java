package com.itrex.java.lab.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceInfo {

    private User user;
    private Coach coach;
    private Date date;
    private SimpleDateFormat startTime;
    private SimpleDateFormat endTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getStartTime() {
        return startTime;
    }

    public void setStartTime(SimpleDateFormat startTime) {
        this.startTime = startTime;
    }

    public SimpleDateFormat getEndTime() {
        return endTime;
    }

    public void setEndTime(SimpleDateFormat endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "user=" + user +
                ", coach=" + coach +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }


}
