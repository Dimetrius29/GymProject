package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.CoachRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

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
    public Coach selectById(Integer id) throws GymException, NotFoundEx {
        Coach coach = null;
        try {
            coach = session.get(Coach.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        Optional<Coach> maybeCoach = Optional.ofNullable(coach);
        coach = maybeCoach.orElseThrow(NotFoundEx::new);
        return coach;
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
        } catch (HibernateException ex) {
            transaction.rollback();
            throw new GymException(ex);
        }
    }
}