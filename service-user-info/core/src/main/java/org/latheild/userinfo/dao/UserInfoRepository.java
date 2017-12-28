package org.latheild.userinfo.dao;

import org.latheild.userinfo.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    public UserInfo findById(String id);

    public UserInfo findByUserId(String userId);

    public int countByUserId(String userId);

    public ArrayList<UserInfo> findAll();
}
