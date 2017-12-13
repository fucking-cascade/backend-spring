package userInfoService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userInfoService.client.UserServiceClient;
import userInfoService.domain.UserInfo;
import userInfoService.repository.UserInfoRepository;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public void createUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        UserInfo tempUserInfo = userInfoRepository.findById(userInfo.getId());
        tempUserInfo.update(userInfo);
        userInfoRepository.save(tempUserInfo);
    }

    @Override
    public UserInfo findUserInfo(int id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public void deleteUserInfo() {
        userInfoRepository.deleteAll();
    }
}
