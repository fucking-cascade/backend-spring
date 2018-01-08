package org.latheild.userinfo.dao;

import org.latheild.userinfo.domain.UserInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface UserInfoRepository {
    UserInfo findById(String id);

    UserInfo findByUserId(String userId);

    int countById(String id);

    int countByUserId(String userId);

    int countByName(String name);

    ArrayList<UserInfo> findAll();

    ArrayList<UserInfo> findAllByName(String name);

    void deleteByUserId(String userId);

    void deleteAll();

    void save(UserInfo userInfo);

    int count();
}
