package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import org.hibernate.Session;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    private final Session session;

    public HibernateUserRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<User> selectAll() {
        return session.createQuery("From User", User.class).list();
    }

    @Override
    public User selectById(Integer id) {
        return session.get(User.class, id);
    }

    @Override
    public List<User> getAllUsersByRole(String role) {
        List users = session.createQuery("Select u from User u JOIN u.roles r Where r.name = :roleName")
                .setParameter("roleName", role)
                .list();
        return users;
    }

    @Override
    public void assignRole(Integer userId, Integer roleId) {
        session.get(User.class, userId)
                .getRoles()
                .add(session.get(Role.class, roleId));
    }

    @Override
    public User add(User user) {
        session.save(user);
        return user;
    }

    @Override
    public void addAll(List<User> users) {
        for (User user : users) {
            session.save(user);
        }
    }

    @Override
    public User update(User user) {
        session.update(user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        User user = session.get(User.class, id);
        session.delete(user);
    }
}