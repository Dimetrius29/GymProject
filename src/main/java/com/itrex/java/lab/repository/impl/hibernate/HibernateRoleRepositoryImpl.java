package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.RoleRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateRoleRepositoryImpl implements RoleRepository {
    private final Session session;

    public HibernateRoleRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Role> selectAll() throws GymException {
        try {
            return session.createQuery("From Role", Role.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Optional<Role> selectById(Integer id) throws GymException {
        Role role = null;
        try {
            role = session.get(Role.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(role);
    }

    @Override
    public Role add(Role role) throws GymException {
        addTransaction(() -> session.save(role));
        return role;
    }

    @Override
    public Role update(Role role) throws GymException {
        addTransaction(() -> session.update(role));
        return role;
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