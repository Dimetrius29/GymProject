package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.GymException;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    List<Role> selectAll() throws GymException;
    Optional <Role> selectById(Integer id) throws GymException;
    Role add(Role role) throws GymException;
    Role update(Role role) throws GymException;
}
