package userInfoService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import userInfoService.domain.UserInfo;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    public UserInfo findById(int id);
}
