package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Membership;
import com.itrex.java.lab.entity.User;

import java.util.List;

public interface MembershipRepository {

    List<Membership> selectAll();
    Membership selectByUser(User user);
    Membership add(Membership membership);
    List<Membership> addAll(List<Membership> memberships);
    Membership update(Membership membership);
    boolean remove(User user);
}
