package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateUserRepositoryImpl implements UserRepository {
    private final Session session;

    public HibernateUserRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<User> selectAll() throws GymException {
        try {
            return session.createQuery("From User", User.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public User selectById(Integer id) throws GymException, NotFoundEx {
        User user = null;
        try {
            user = session.get(User.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        Optional<User> maybeUser = Optional.ofNullable(user);
        user = maybeUser.orElseThrow(NotFoundEx::new);
        return user;
    }

    @Override
    public List<User> getAllUsersByRole(String role) throws GymException, NotFoundEx {
        List users = null;
        try {
            users = session.createQuery("Select u from User u JOIN u.roles r Where r.name = :roleName")
                    .setParameter("roleName", role)
                    .list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
        Optional<List> maybeUser = Optional.ofNullable(users);
        users = maybeUser.orElseThrow(NotFoundEx::new);
        return users;
    }

    @Override
    public void assignRole(Integer userId, Integer roleId) throws GymException {
        addTransaction(() -> session.get(User.class, userId)
                .getRoles()
                .add(session.get(Role.class, roleId)));
        addTransaction(() -> session.get(Role.class, roleId).getUsers()
                .add(session.get(User.class, userId)));
    }

    @Override
    public User add(User user) throws GymException {
        addTransaction(() -> session.save(user));
        return user;
    }

    @Override
    public void addAll(List<User> users) throws GymException {
        for (User user : users) {
            addTransaction(() -> session.save(user));
        }
    }

    @Override
    public User update(User user) throws GymException {
        addTransaction(() -> session.update(user));
        return user;
    }

    @Override
    public void delete(Integer id) throws GymException, NotFoundEx {
        User user = null;
        try {
            user = session.get(User.class, id);
            Optional<User> maybeUser = Optional.ofNullable(user);
            user = maybeUser.orElseThrow(NotFoundEx::new);
            User finalUser = user;
            addTransaction(() -> session.delete(finalUser));
        } catch (Exception ex) {
            throw new GymException(ex);
        }
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