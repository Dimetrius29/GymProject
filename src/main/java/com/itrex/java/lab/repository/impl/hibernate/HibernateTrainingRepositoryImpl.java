package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.TrainingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class HibernateTrainingRepositoryImpl implements TrainingRepository {
    private final SessionFactory sessionFactory;

    public HibernateTrainingRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Training> selectAll() throws GymException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Training", Training.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Training add(Training training) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.save(training));
            return training;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Training update(Training training) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.update(training));
            return training;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void deleteTrainingByCoach(Integer coachId) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.createQuery("Delete From Training WHERE coach_id = :id")
                    .setParameter("id", coachId)
                    .executeUpdate());
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    private void addTransaction(Runnable runnable) throws GymException {
        Session session = sessionFactory.openSession();
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