package org.latheild.userinfo.dao;

import org.latheild.userinfo.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    UserInfo findById(String id);

    UserInfo findByUserId(String userId);

    int countById(String id);

    int countByUserId(String userId);

    int countByName(String name);

    ArrayList<UserInfo> findAll();

    ArrayList<UserInfo> findAllByName(String name);

    void deleteByUserId(String userId);
}
