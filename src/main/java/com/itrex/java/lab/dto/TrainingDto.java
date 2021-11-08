package com.itrex.java.lab.dto;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class TrainingDto {

    private UserDto user;
    private CoachDto coach;
    private Date date;
    private Time startTime;
    private Time endTime;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CoachDto getCoach() {
        return coach;
    }

    public void setCoach(CoachDto coach) {
        this.coach = coach;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "\nTrainingDTO{" +
                "user=" + user +
                ", coach=" + coach +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingDto that = (TrainingDto) o;
        return Objects.equals(user, that.user) && coach.equals(that.coach) && date.equals(that.date) && startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, coach, date, startTime, endTime);
    }
}
