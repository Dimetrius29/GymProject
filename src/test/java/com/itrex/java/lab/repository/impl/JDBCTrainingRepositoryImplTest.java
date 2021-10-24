package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.TrainingRepository;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JDBCTrainingRepositoryImplTest extends BaseRepositoryTest {

    private final TrainingRepository repository;

    public JDBCTrainingRepositoryImplTest() {
        super();
        repository = new JDBCTrainingRepositoryImpl(getConnectionPool());
    }

    @Test
    public void selectAll_validData_shouldReturnExistUsersTraining() throws GymException {
        //given && when
        int expected = 6;
        final List<Training> result = repository.selectAll();
        int actual = result.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectAllTrainings_validData_shouldThrowExceptionTest() {
        //given
        cleanDB();
        Exception thrownEx = null;

        //when
        try {
            repository.selectAll();
        } catch (GymException ex) {
            thrownEx = ex;
        }

        //then
        assertNotNull(thrownEx);
    }

    @Test
    public void deleteTrainingByCoach_validData_shouldDeleteExistTrainingTest() throws GymException {
        //given
        int coachId = 1;
        int expected = 2;

        // when
        repository.deleteTrainingByCoach(coachId);
        List<Training> trainings = repository.selectAll();
        int actual = trainings.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void deleteTrainingByCoach_inValidData_shouldThrowExceptionTest() {
        //given
        cleanDB();
        Exception thrownEx = null;

        //when
        try {
            repository.deleteTrainingByCoach(2);
        } catch (GymException ex) {
            thrownEx = ex;
        }

        //then
        assertNotNull(thrownEx);
    }
}
