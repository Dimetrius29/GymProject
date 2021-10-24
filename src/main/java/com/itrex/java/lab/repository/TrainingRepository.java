package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.exception.GymException;

import java.util.List;

public interface TrainingRepository {

    List<Training> selectAll() throws GymException;
    Training add(Training training) throws GymException;
    Training update(Training training) throws GymException;
    void deleteTrainingByCoach(Integer coachId) throws GymException;
}
