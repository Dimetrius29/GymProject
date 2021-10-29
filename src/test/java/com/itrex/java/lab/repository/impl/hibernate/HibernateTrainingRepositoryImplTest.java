package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.TrainingRepository;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HibernateTrainingRepositoryImplTest extends BaseRepositoryTest {
    private final TrainingRepository repository;

    public HibernateTrainingRepositoryImplTest() {
        super();
        repository = new HibernateTrainingRepositoryImpl(getSession());
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
        //given && when && then
        cleanDB();
        assertThrows(GymException.class, () -> repository.selectAll());
    }

    @Test
    public void deleteTrainingByCoach_validData_shouldDeleteExistTrainingTest() throws GymException{
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
    public void deleteTrainingByCoach_inValidData_shouldDeleteTrainingsTest() throws GymException {
        //given && when && then

        int id = 1;
        int expected = 2;

        // when
        repository.deleteTrainingByCoach(id);
        List<Training> trainings = repository.selectAll();
        int actual = trainings.size();

        //then
        assertEquals(actual, expected);
    }
}
