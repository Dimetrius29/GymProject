package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.ServiceInfo;
import com.itrex.java.lab.entity.User;

import java.util.List;

public interface ServiceInfoRepository {

    List<ServiceInfo> selectAll();
    ServiceInfo selectByUser(User user);
    ServiceInfo add(ServiceInfo serviceInfo);
    List<ServiceInfo> addAll(List<ServiceInfo> ServiceInfoes);
    ServiceInfo update(ServiceInfo serviceInfo);
    boolean remove(User user);
}
