package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Coach;

import java.util.List;

public interface CoachRepository {

    List<Coach> selectAll();
    Coach selectById(Integer id);
    Coach add(Coach coach);
    List<Coach> addAll(List<Coach> coaches);
    Coach update(Coach coach);
    void delete(Integer id);
}
