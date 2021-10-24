package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.exception.GymException;

import java.util.List;

public interface CoachRepository {

    List<Coach> selectAll() throws GymException;
    Coach selectById(Integer id) throws GymException;
    Coach add(Coach coach) throws GymException;
    List<Coach> addAll(List<Coach> coaches) throws GymException;
    Coach update(Coach coach) throws GymException;
    void deleteCoach(Integer id) throws GymException;
    void deleteTrainingByCoach(Integer coachId) throws GymException;
}
