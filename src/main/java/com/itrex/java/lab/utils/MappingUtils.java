package com.itrex.java.lab.utils;

import com.itrex.java.lab.dto.CoachDto;
import com.itrex.java.lab.dto.RoleDto;
import com.itrex.java.lab.dto.TrainingDto;
import com.itrex.java.lab.dto.UserDto;
import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.entity.User;

public class MappingUtils {

    public static User mapToUserEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPhone(userDto.getPhone());

        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPhone(user.getPhone());

        return userDto;
    }

    public static Coach mapToCoachEntity(CoachDto coachDto) {
        Coach coach = new Coach();
        coach.setId(coachDto.getId());
        coach.setName(coachDto.getName());
        coach.setSurname(coachDto.getSurname());
        coach.setPhone(coachDto.getPhone());
        coach.setSpecialization(coachDto.getSpecialization());
        coach.setPriceOfActivity(coachDto.getPriceOfActivity());

        return coach;
    }

    public static CoachDto mapToCoachDto(Coach coach) {
        CoachDto coachDto = new CoachDto();
        coachDto.setId(coach.getId());
        coachDto.setName(coach.getName());
        coachDto.setSurname(coach.getSurname());
        coachDto.setPhone(coach.getPhone());
        coachDto.setSpecialization(coach.getSpecialization());
        coachDto.setPriceOfActivity(coach.getPriceOfActivity());

        return coachDto;
    }

    public static Role mapToRoleEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        ;
        role.setName(roleDto.getName());

        return role;
    }

    public static RoleDto mapToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        ;
        roleDto.setName(role.getName());

        return roleDto;
    }

    public static Training mapToTrainingEntity(TrainingDto trainingDto) {
        Training training = new Training();
        training.setUser(mapToUserEntity(trainingDto.getUser()));
        ;
        training.setCoach(mapToCoachEntity(trainingDto.getCoach()));
        training.setDate(trainingDto.getDate());
        training.setStartTime(trainingDto.getStartTime());
        training.setEndTime(trainingDto.getEndTime());

        return training;
    }

    public static TrainingDto mapToTrainingDto(Training training) {
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setUser(mapToUserDto(training.getUser()));
        ;
        trainingDto.setCoach(mapToCoachDto(training.getCoach()));
        trainingDto.setDate(training.getDate());
        trainingDto.setStartTime(training.getStartTime());
        trainingDto.setEndTime(training.getEndTime());

        return trainingDto;
    }
}