package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;

import java.util.List;

public interface UserRepository {

    List<User> selectAll() throws GymException;
    User selectById(Integer id) throws GymException, NotFoundEx;
    List<User> getAllUsersByRole(String roleName) throws GymException;
    void assignRole(Integer userId, Integer roleId) throws GymException;
    User add(User user) throws GymException;
    void addAll(List<User> users) throws GymException;
    User update(User user) throws GymException;
    void delete(Integer id) throws GymException;
}

