package org.latheild.userinfo.dao;

import org.latheild.userinfo.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    public UserInfo findById(String id);

    public UserInfo findByUserId(String userId);

    public int countById(String id);

    public int countByUserId(String userId);

    public int countByName(String name);

    public ArrayList<UserInfo> findAll();

    public ArrayList<UserInfo> findAllByName(String name);

    public void deleteByUserId(String userId);
}
