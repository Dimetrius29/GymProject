package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> selectAll() throws GymException;
    Optional<User> selectById(Integer id) throws GymException;
    Optional<List> getAllUsersByRole(String roleName) throws GymException;
    void assignRole(Integer userId, Integer roleId) throws GymException;
    User add(User user) throws GymException;
    void addAll(List<User> users) throws GymException;
    User update(User user) throws GymException;
    void delete(Integer id) throws GymException;
}

