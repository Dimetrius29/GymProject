package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.entity.User;

import java.util.List;

public interface TrainingRepository {

    List<Training> selectAll();
    Training add(Training training);
    List<Training> addAll(List<Training> trainings);
    Training update(Training training, User user, Coach coach);
    void delete(User user);
}
