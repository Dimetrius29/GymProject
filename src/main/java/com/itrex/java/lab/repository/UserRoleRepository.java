package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.UserRole;
import java.util.List;

public interface UserRoleRepository {

    List<UserRole> selectAll();
    UserRole add(UserRole userRole);
    List<UserRole> addAll(List<UserRole> userRoles);
    UserRole update(UserRole userRole);
    boolean delete(UserRole userRole);
}
