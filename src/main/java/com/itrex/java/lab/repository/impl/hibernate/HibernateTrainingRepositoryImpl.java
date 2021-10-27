package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.repository.TrainingRepository;
import org.hibernate.Session;

import java.util.List;

public class HibernateTrainingRepositoryImpl implements TrainingRepository {
    private final Session session;

    public HibernateTrainingRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Training> selectAll() {
        return session.createQuery("From Training", Training.class).list();
    }

    @Override
    public Training add(Training training) {
        session.save(training);
        return training;
    }

    @Override
    public Training update(Training training) {
        session.update(training);
        return training;
    }

    @Override
    public void deleteTrainingByCoach(Integer coachId) {
        session.createQuery("Delete From Training WHERE coach_id = :id")
                .setParameter("id", coachId)
                .executeUpdate();
    }
}