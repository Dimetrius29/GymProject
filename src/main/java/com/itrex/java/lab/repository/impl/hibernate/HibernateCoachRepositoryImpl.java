package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.CoachRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateCoachRepositoryImpl implements CoachRepository {
    private final SessionFactory sessionFactory;

    public HibernateCoachRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Coach> selectAll() throws GymException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Coach", Coach.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Optional<Coach> selectById(Integer id) throws GymException {
        Coach coach = null;
        try (Session session = sessionFactory.openSession()) {
            coach = session.get(Coach.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(coach);
    }

    @Override
    public Coach add(Coach coach) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.save(coach));
            return coach;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void addAll(List<Coach> coaches) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            for (Coach coach : coaches) {
                addTransaction(() -> session.save(coach));
            }
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Coach update(Coach coach) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.update(coach));
            return coach;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void deleteCoach(Integer id) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            Coach coach = session.get(Coach.class, id);
            addTransaction(() -> session.delete(coach));
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