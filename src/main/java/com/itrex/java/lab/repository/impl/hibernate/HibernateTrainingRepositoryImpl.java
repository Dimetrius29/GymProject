package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.TrainingRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateTrainingRepositoryImpl implements TrainingRepository {
    private final Session session;

    public HibernateTrainingRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Training> selectAll() throws GymException {
        try {
            return session.createQuery("From Training", Training.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Training add(Training training) throws GymException {
        addTransaction(() -> session.save(training));
        return training;
    }

    @Override
    public Training update(Training training) throws GymException {
        addTransaction(() -> session.update(training));
        return training;
    }

    @Override
    public void deleteTrainingByCoach(Integer coachId) throws GymException {
        addTransaction(() -> session.createQuery("Delete From Training WHERE coach_id = :id")
                .setParameter("id", coachId)
                .executeUpdate());
    }

    private void addTransaction(Runnable runnable) throws GymException {
        Transaction transaction = session.beginTransaction();
        try {
            runnable.run();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new GymException(ex);
        }
    }
}