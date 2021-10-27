package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.repository.CoachRepository;
import org.hibernate.Session;

import java.util.List;

public class HibernateCoachRepositoryImpl implements CoachRepository {
    private final Session session;

    public HibernateCoachRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Coach> selectAll() {
        return session.createQuery("From Coach", Coach.class).list();
    }

    @Override
    public Coach selectById(Integer id) {
        return session.get(Coach.class, id);
    }

    @Override
    public Coach add(Coach coach) {
        session.save(coach);
        return coach;
    }

    @Override
    public void addAll(List<Coach> coaches) {
        for (Coach coach : coaches) {
            session.save(coach);
        }
    }

    @Override
    public Coach update(Coach coach) {
        session.update(coach);
        return coach;
    }

    @Override
    public void deleteCoach(Integer id) {
        Coach coach = session.get(Coach.class, id);
        session.delete(coach);
    }
}