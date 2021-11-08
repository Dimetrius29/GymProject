package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.dto.UserDto;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.service.UserService;
import com.itrex.java.lab.utils.MappingUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDto> selectAll() throws GymException {
        List<UserDto> usersDto = new ArrayList<>();
        try {
            for (User user : repository.selectAll()) {
                usersDto.add(MappingUtils.mapToUserDto(user));
            }
        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return usersDto;
    }

    @Override
    public Optional<UserDto> selectById(Integer id) throws GymException {
        UserDto userDto = null;
        try {
            Optional<User> user = repository.selectById(id);
            if (user.isPresent()) {
                userDto = MappingUtils.mapToUserDto(user.get());
            }
        } catch (Exception ex) {
            throw new GymException(ex);
        }
        return Optional.ofNullable(userDto);
    }

    @Override
    public Optional<List> getAllUsersByRole(String roleName) throws GymException {
        return Optional.empty();
    }

    @Override
    public void assignRole(Integer userId, Integer roleId) throws GymException {

    }

    @Override
    public UserDto add(UserDto userDto) throws GymException {
        try {
            return MappingUtils.mapToUserDto(repository.add(MappingUtils.mapToUserEntity(userDto)));
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void addAll(List<UserDto> usersDto) throws GymException {


    }

    @Override
    public UserDto update(UserDto userDto) throws GymException {
        try {
            User user = repository.update(MappingUtils.mapToUserEntity(userDto));
            return MappingUtils.mapToUserDto(user);
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }

    @Override
    public void delete(Integer id) throws GymException {
        try {
            repository.delete(id);
        } catch (Exception ex) {
            throw new GymException(ex);
        }
    }
}