package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateRoleRepositoryImpl implements RoleRepository {
    private final SessionFactory sessionFactory;

    public HibernateRoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> selectAll() throws GymException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Role", Role.class).list();
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Optional<Role> selectById(Integer id) throws GymException {
        Role role = null;
        try (Session session = sessionFactory.openSession()) {
            role = session.get(Role.class, id);

        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(role);
    }

    @Override
    public Role add(Role role) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.save(role));
            return role;
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public Role update(Role role) throws GymException {
        try (Session session = sessionFactory.openSession()) {
            addTransaction(() -> session.update(role));
            return role;
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