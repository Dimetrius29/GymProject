package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;

import java.sql.SQLException;
import java.util.List;

public interface RoleRepository {

    List<Role> selectAll() throws GymException;
    List<Role> getAllUserRoles(Integer userId) throws GymException, SQLException, NotFoundEx;
    Role selectById(Integer id) throws GymException, NotFoundEx;
    Role add(Role role) throws GymException;
    Role update(Role role) throws GymException;
    void deleteRole(Integer id) throws GymException;
}
