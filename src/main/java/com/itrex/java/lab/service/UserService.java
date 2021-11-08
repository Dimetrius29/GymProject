package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.UserDto;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> selectAll() throws GymException;
    Optional<UserDto> selectById(Integer id) throws GymException;
    Optional<List> getAllUsersByRole(String roleName) throws GymException;
    void assignRole(Integer userId, Integer roleId) throws GymException;
    UserDto add(UserDto user) throws GymException;
    void addAll(List<UserDto> users) throws GymException;
    UserDto update(UserDto user) throws GymException;
    void delete(Integer id) throws GymException;
}

