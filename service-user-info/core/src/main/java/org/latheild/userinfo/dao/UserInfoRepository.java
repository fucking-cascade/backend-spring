package org.latheild.userinfo.dao;

import org.latheild.userinfo.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    public UserInfo findById(Long id);

    public UserInfo findByUserId(Long userId);

    public int countByUserId(Long userId);
}
