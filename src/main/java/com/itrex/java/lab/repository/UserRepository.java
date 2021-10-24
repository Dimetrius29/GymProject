package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.impl.JDBCUserRepositoryImpl;

import java.util.List;

public interface UserRepository {

    List<User> selectAll() throws GymException;
    User selectById(Integer id) throws GymException, JDBCUserRepositoryImpl.NotFoundEx;
    List<User> getAllUsersByRole(String role) throws GymException;
    void assignRole(Integer userId, Integer roleId) throws GymException;
    User add(User user) throws GymException;
    void addAll(List<User> users) throws GymException;
    User update(User user) throws GymException;
    void delete(Integer id) throws GymException;
    void deleteUserFromLinkedTableById(Integer userId) throws GymException;
    void deleteUserFromTrainingById(Integer userId) throws GymException;
}

