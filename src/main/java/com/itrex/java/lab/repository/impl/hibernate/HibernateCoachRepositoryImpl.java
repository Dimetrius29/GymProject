package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.CoachRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateCoachRepositoryImpl implements CoachRepository {
    private final Session session;

    public HibernateCoachRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Coach> selectAll() throws GymException {
        try {
            return session.createQuery("From Coach", Coach.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Optional<Coach> selectById(Integer id) throws GymException {
        Coach coach = null;
        try {
            coach = session.get(Coach.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(coach);
    }

    @Override
    public Coach add(Coach coach) throws GymException {
        addTransaction(() -> session.save(coach));
        return coach;
    }

    @Override
    public void addAll(List<Coach> coaches) throws GymException {
        for (Coach coach : coaches) {
            addTransaction(() -> session.save(coach));
        }
    }

    @Override
    public Coach update(Coach coach) throws GymException {
        addTransaction(() -> session.update(coach));
        return coach;
    }

    @Override
    public void deleteCoach(Integer id) throws GymException {
        Coach coach = session.get(Coach.class, id);
        addTransaction(() -> session.delete(coach));
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