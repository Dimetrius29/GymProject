package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Role;
import java.util.List;

public interface RoleRepository {

    List<Role> selectAll();
    Role add(Role role);
    List<Role> addAll(List<Role> roles);
    Role update(Role role);
    boolean remove(Integer id);
}
