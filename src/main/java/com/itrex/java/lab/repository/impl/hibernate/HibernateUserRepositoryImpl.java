package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateUserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public HibernateUserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> selectAll() throws GymException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From User", User.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Optional<User> selectById(Integer id) throws GymException {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.get(User.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<List> getAllUsersByRole(String role) throws GymException {
        List users = null;
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("Select u from User u JOIN u.roles r Where r.name = :roleName")
                    .setParameter("roleName", role)
                    .list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(users);
    }

    @Override
    public void assignRole(Integer userId, Integer roleId) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.get(User.class, userId)
                    .getRoles()
                    .add(session.get(Role.class, roleId)));
            addTransaction(() -> session.get(Role.class, roleId).getUsers()
                    .add(session.get(User.class, userId)));
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public User add(User user) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.save(user));
            return user;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void addAll(List<User> users) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            for (User user : users) {
                addTransaction(() -> session.save(user));
            }
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public User update(User user) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.update(user));
            return user;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void delete(Integer id) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            addTransaction(() -> session.delete(user));
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