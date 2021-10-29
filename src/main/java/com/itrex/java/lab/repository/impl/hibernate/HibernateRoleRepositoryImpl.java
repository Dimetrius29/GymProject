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
    public List<Role> getAllUserRoles(Integer userId) throws GymException, NotFoundEx {
        List roles = null;
        try {
            roles = session.createQuery("Select r from Role r JOIN r.users u Where u.id = :id")
                    .setParameter("id", userId)
                    .list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
        Optional<List> maybeRole = Optional.ofNullable(roles);
        roles = maybeRole.orElseThrow(NotFoundEx::new);
        return roles;
    }

    @Override
    public Role selectById(Integer id) throws GymException, NotFoundEx {
        Role role = null;
        try {
            role = session.get(Role.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        Optional<Role> maybeRole = Optional.ofNullable(role);
        role = maybeRole.orElseThrow(NotFoundEx::new);
        return role;
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

    @Override
    public void deleteRole(Integer id) throws GymException {
        Role role = session.get(Role.class, id);
        addTransaction(() -> session.delete(role));
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