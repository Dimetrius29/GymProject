package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.RoleRepository;
import org.hibernate.Session;

import javax.transaction.Transactional;

import java.util.List;

public class HibernateRoleRepositoryImpl implements RoleRepository {
    private final Session session;

    public HibernateRoleRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Role> selectAll() {
        return session.createQuery("From Role", Role.class).list();
    }

    @Override
    public List<Role> getAllUserRoles(Integer userId) {
        List roles = session.createQuery("Select r from Role r JOIN r.users u Where u.id = :id")
                .setParameter("id", userId)
                .list();
        return roles;
    }

    @Override
    public Role selectById(Integer id) {
        return session.get(Role.class, id);
    }

    @Override
    public Role add(Role role) {
        session.save(role);
        return role;
    }

    @Override
    public Role update(Role role) {
        session.update(role);
        return role;
    }

    @Override
    public void deleteRole(Integer id) {
        Role role = session.get(Role.class, id);
        session.delete(role);
    }
}