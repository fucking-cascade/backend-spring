package userInfoService.service;

import userInfoService.domain.UserInfo;

public interface UserInfoService {
    public void createUserInfo(UserInfo userInfo);

    public void updateUserInfo(UserInfo userInfo);

    public UserInfo findUserInfo(int id);

    public void deleteUserInfo();
}
