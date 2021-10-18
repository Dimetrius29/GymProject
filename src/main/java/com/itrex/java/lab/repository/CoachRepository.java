package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Coach;
import java.util.List;

public interface CoachRepository {

    List<Coach> selectAll();
    Coach add(Coach coach);
    List<Coach> addAll(List<Coach> coaches);
    Coach update(Coach coach);
    boolean remove(Integer id);
}
