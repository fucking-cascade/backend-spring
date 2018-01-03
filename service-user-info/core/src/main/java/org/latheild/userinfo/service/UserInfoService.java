package org.latheild.userinfo.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.dto.UserInfoDTO;

import java.util.ArrayList;

public interface UserInfoService {
    public UserInfoDTO register(RegisterDTO registerDTO);

    public UserInfoDTO updateUserInfo(UserInfoDTO userInfoDTO);

    public UserInfoDTO getUserInfoByUserId(String userId);

    public ArrayList<UserInfoDTO> getUserInfosByName(String name);

    public ArrayList<UserInfoDTO> getAllUserInfos();

    public void resetUserInfoById(String userId, String code);

    public void deleteAllUserInfos(String code);

    public void deleteUserInfoByUserId(String userId, String code);
}
