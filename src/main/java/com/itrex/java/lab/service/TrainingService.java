package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TrainingDto;
import com.itrex.java.lab.exception.GymException;

import java.util.List;

public interface TrainingService {

    List<TrainingDto> selectAll() throws GymException;
    TrainingDto add(TrainingDto training) throws GymException;
    TrainingDto update(TrainingDto training) throws GymException;
    void deleteTrainingByCoach(Integer coachId) throws GymException;
}
