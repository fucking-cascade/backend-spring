package org.latheild.userinfo.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.dto.UserInfoDTO;

import java.util.ArrayList;

public interface UserInfoService {
    UserInfoDTO register(RegisterDTO registerDTO);

    UserInfoDTO updateUserInfo(UserInfoDTO userInfoDTO);

    UserInfoDTO getUserInfoByUserId(String userId);

    ArrayList<UserInfoDTO> getUserInfosByName(String name);

    ArrayList<UserInfoDTO> getAllUserInfos();

    void resetUserInfoById(String userId, String code);

    void deleteAllUserInfos(String code);

    void deleteUserInfoByUserId(String userId, String code);
}
